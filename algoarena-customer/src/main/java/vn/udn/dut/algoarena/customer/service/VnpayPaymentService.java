package vn.udn.dut.algoarena.customer.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.common.core.utils.DateUtils;
import vn.udn.dut.algoarena.common.core.utils.ServletUtils;
import vn.udn.dut.algoarena.customer.domain.vo.PaymentInfoVo;
import vn.udn.dut.algoarena.port.constant.PaymentConstants;
import vn.udn.dut.algoarena.port.domain.bo.VnpHistoryBo;
import vn.udn.dut.algoarena.port.service.IVnpHistoryService;
import vn.udn.dut.algoarena.system.constant.SystemConstants;
import vn.udn.dut.algoarena.system.service.ISysDictDataService;

/**
 * 
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class VnpayPaymentService {

	private final ISysDictDataService dictDataService;
	private final IVnpHistoryService vnpHistoryService;

	public void setPickupFullPaymentInfo(PaymentInfoVo paymentInfo) {
		paymentInfo.setOrderInfo("Thanh toan ve xem phim");
		String transactionId = paymentInfo.getShowtimeId() + DateUtils.parseDateToStr("yyyyMMddHHmmss", new Date());
		paymentInfo.setTransactionId(transactionId);
	}

	private static String hmacSHA512(final String key, final String data) {
		try {
			if (key == null || data == null) {
				throw new NullPointerException();
			}
			final Mac hmac512 = Mac.getInstance("HmacSHA512");
			byte[] hmacKeyBytes = key.getBytes();
			final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
			hmac512.init(secretKey);
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] result = hmac512.doFinal(dataBytes);
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (Exception ex) {
			return "";
		}
	}

	private String getPaymentUrl(String transactionId, String seatIdsStr, Long amount, String remark, Long algoarenaId)
			throws UnsupportedEncodingException {
		String vnp_PayUrl = dictDataService.selectDictLabel(SystemConstants.VNPAY_CONFIG_DICT_TYPE, "vnp_PayUrl");
		String vnp_ReturnUrl = dictDataService.selectDictLabel(SystemConstants.VNPAY_CONFIG_DICT_TYPE, "vnp_ReturnUrl");
		String vnp_TmnCode = dictDataService.selectDictLabel(SystemConstants.VNPAY_CONFIG_DICT_TYPE, "vnp_TmnCode");
		String secretKey = dictDataService.selectDictLabel(SystemConstants.VNPAY_CONFIG_DICT_TYPE, "secretKey");

		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String orderType = "other";

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", amount * 100 + "");
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_TxnRef", transactionId);
		vnp_Params.put("vnp_OrderInfo", remark);
		vnp_Params.put("vnp_OrderType", orderType);

		String locate = "vn";
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", ServletUtils.getClientIP());

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List<String> fieldNames = new ArrayList<String>(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator<String> itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = hmacSHA512(secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = vnp_PayUrl + "?" + queryUrl;

		// Insert history
		VnpHistoryBo vnpHistory = new VnpHistoryBo();
		vnpHistory.setAlgoArenaId(algoarenaId);
		vnpHistory.setAmount(amount);
		vnpHistory.setOrderInfo(remark);
		vnpHistory.setVnpTransactionId(transactionId);
		vnpHistory.setStatus(PaymentConstants.VNPAY_STATUS_PROCESS);
		vnpHistory.setTransactionId(seatIdsStr);
		vnpHistory.setSecureHashType("hmacSHA512");
		vnpHistory.setSecureHash(vnp_SecureHash);
		vnpHistoryService.insertByBo(vnpHistory);
		return paymentUrl;
	}
}
