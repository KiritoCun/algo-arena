package vn.udn.dut.algoarena.publicapi.controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.common.core.domain.R;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.port.domain.bo.*;
import vn.udn.dut.algoarena.port.domain.vo.*;
import vn.udn.dut.algoarena.port.service.*;
import vn.udn.dut.algoarena.publicapi.helper.JsonToJavaDeclaration;
import vn.udn.dut.algoarena.publicapi.helper.MethodExtractorHelper;
import vn.udn.dut.algoarena.publicapi.service.HomepageSearchService;

/**
 * Homepage api
 *
 * @author HOALD
 * @date 2023-11-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/homepage/search")
public class HomepageSearchController extends BaseController {

    private final IPromotionService promotionService;
    private final IMovieService movieService;
    private final ISlideService slideService;
    private final IAlgoArenaService algoarenaService;
    private final IShowtimeService showtimeService;
    private final ISeatService seatService;
    private final IVnpHistoryService vnpHistoryService;
    private final ISeatTypeService seatTypeService;
    private final IBookingService bookingService;
    private final IBookingDetailService bookingDetailService;
    private final IHallService hallService;

    private final IProblemService problemService;
    private final ITestcaseService testcaseService;

    @GetMapping("/problem")
    public TableDataInfo<ProblemVo> publicList(ProblemBo bo, PageQuery pageQuery) {
        return problemService.queryPagePublicList(bo, pageQuery);
    }

    @PostMapping("/submit-solution")
    public List<Boolean> submitSolution(@RequestBody Map<String, String> request) {
        String submittedCode = request.get("submittedCode");
        String keyPath = request.get("problemId");
        String language = request.get("language");
        String version = request.get("version");

        ProblemBo problemBo = new ProblemBo();
        problemBo.setKeyPath(keyPath);
        List<ProblemVo> problemVoList = problemService.queryPublicList(problemBo);
        ProblemVo problemVo = problemVoList.get(0);
        TestcaseBo testcaseBo = new TestcaseBo();
        testcaseBo.setProblemId(problemVo.getId());
        List<TestcaseVo> testcaseList = testcaseService.queryList(testcaseBo);

        return testcaseList.stream()
                .map(testcaseVo -> HomepageSearchService.submitSolution(submittedCode, language, version, testcaseVo))
                .collect(Collectors.toList());
    }

    @GetMapping("/documents")
    public R<Map<String, Object>> getDocuments() {
        Map<String, Object> result = new HashMap<String, Object>();
        PromotionBo bo1 = new PromotionBo();
        result.put("promotions", promotionService.queryList(bo1));
        MovieBo bo2 = new MovieBo();
        result.put("movies", movieService.queryList(bo2));
        SlideBo bo3 = new SlideBo();
        result.put("slides", slideService.queryList(bo3));
        return R.ok(result);
    }
    
    @GetMapping("/nowplayingmovies")
    public List<MovieVo> getNowPlayingMovies() {
        return movieService.getNowPlayingMovies();
    }
    
    @GetMapping("/upcommingmovies")
    public List<MovieVo> getUpcommingMovies() {
        return movieService.getUpcomingMovies();
    }

    @GetMapping("/promotions")
    public List<PromotionVo> getPromotions() {
        return promotionService.getNowPromotions();
    }
    @GetMapping("/algoarenas")
    public List<AlgoArenaVo> getAlgoArenaList(AlgoArenaBo bo) {
        return algoarenaService.queryList(bo);
    }
    
    @GetMapping("/showtimeInfoList/{movieId}")
    public List<ShowtimeInfoVo> fetchShowtimeInfoList(@PathVariable Long movieId) {
        return showtimeService.fetchShowtimeInfoList(movieId);
    }
    
    @GetMapping("/showtimeInfoList")
    public List<ShowtimeVo> getShowtimeList(ShowtimeBo bo) {
        return showtimeService.queryList(bo);    }
    
    @GetMapping("/showtime/seatOrderList/{showtimeId}")
    public List<SeatOrderVo> fetchSeatOrderList(@PathVariable Long showtimeId) {
        return seatService.fetchSeatOrderList(showtimeId);
    }

    /**
     * Handle payment successfully
     */
    @GetMapping("/booking/{transactionId}")
    public R<InvoiceInfoVo> handleBookingTicket(@PathVariable String transactionId) {
        InvoiceInfoVo invoiceInfo = new InvoiceInfoVo();
        VnpHistoryBo vnpHistoryParam = new VnpHistoryBo();
        vnpHistoryParam.setVnpTransactionId(transactionId);
        VnpHistoryVo vnpHistory = vnpHistoryService.queryList(vnpHistoryParam).get(0);
        String seatIdsStr = vnpHistory.getTransactionId();
        String[] seatIds = seatIdsStr.split(",");
        SeatVo seat = seatService.queryById(Long.parseLong(seatIds[0]));
        ShowtimeVo showtime = showtimeService.queryById(seat.getShowtimeId());
        BookingBo booking = new BookingBo();
        booking.setAlgoArenaId(showtime.getAlgoArenaId());
        booking.setCustomerId(vnpHistory.getCreateBy());
        booking.setPaymentFlag("Y");
        booking.setNumTicket((long) seatIds.length);
        if (!"00".equals(transactionId.split("_")[1])) {
            Long promotionId = Long.parseLong(transactionId.split("_")[1]);
            booking.setPromotionId(promotionId);
            PromotionVo promotion = promotionService.queryById(promotionId);
            invoiceInfo.setPromotionName(promotion.getPromotionDescription());
        }
        booking.setShowtimeId(showtime.getId());
        booking.setTotalPrice(vnpHistory.getAmount());
        bookingService.insertByBo(booking);
        invoiceInfo.setTicketId(booking.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
        // Chuyển đối tượng Date thành chuỗi
        String formattedDate = sdf.format(showtime.getStartTime());
        invoiceInfo.setStartTime(formattedDate);
        AlgoArenaVo algoarena = algoarenaService.queryById(showtime.getAlgoArenaId());
        invoiceInfo.setAlgoArenaName(algoarena.getAlgoArenaName());
        HallVo hall = hallService.queryById(showtime.getHallId());
        invoiceInfo.setHallName(hall.getHallName());
        Long totalAmount = 0L;
        String seatName = "";
        List<SeatVo> seatList = new ArrayList<>();
        for (String seatIdStr : seatIds) {
            Long seatId = Long.parseLong(seatIdStr);
            SeatVo seatObj = seatService.queryById(seatId);
            seatList.add(seatObj);
            SeatTypeVo seatType = seatTypeService.queryById(seatObj.getSeatTypeId());
            totalAmount += seatType.getPrice();
            seatName += seatObj.getRowCode() + seatObj.getColumnCode() + ", ";
            BookingDetailBo bookingDetail = new BookingDetailBo();
            bookingDetail.setBookingId(booking.getId());
            bookingDetail.setSeatId(seatId);
            bookingDetail.setAlgoArenaId(showtime.getAlgoArenaId());
            bookingDetailService.insertByBo(bookingDetail);
            SeatBo seatUpdate = new SeatBo();
            seatUpdate.setStatus("Y");
            seatUpdate.setId(seatId);
            seatService.updateByBo(seatUpdate);
        }
        for (SeatVo seatVo : seatList) {
            SeatTypeVo seatType = seatTypeService.queryById(seatVo.getSeatTypeId());
            seatVo.setSeatTypeName(seatType.getSeatTypeName());
        }
        Map<String, Long> summary = seatList.stream()
                .collect(Collectors.groupingBy(SeatVo::getSeatTypeName, Collectors.counting()));

        String seatDescription = summary.entrySet().stream().map(entry -> entry.getValue() + " x " + entry.getKey())
                .collect(Collectors.joining(", "));
        invoiceInfo.setSeatName(seatName.substring(0, seatName.length() - 2));
        invoiceInfo.setSeatDescription(seatDescription);
        invoiceInfo.setTotalAmount(totalAmount);
        invoiceInfo.setActualAmount(vnpHistory.getAmount());
        invoiceInfo.setDiscountAmount(totalAmount - vnpHistory.getAmount());
        return R.ok(invoiceInfo);
    }
}
