package vn.udn.dut.algoarena.customer.controller;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.common.core.domain.R;
import vn.udn.dut.algoarena.common.core.utils.StringUtils;
import vn.udn.dut.algoarena.common.core.utils.file.MimeTypeUtils;
import vn.udn.dut.algoarena.common.log.annotation.Log;
import vn.udn.dut.algoarena.common.log.enums.BusinessType;
import vn.udn.dut.algoarena.common.satoken.utils.LoginHelper;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.port.domain.vo.CustomerProfileVo;
import vn.udn.dut.algoarena.port.domain.vo.CustomerVo;
import vn.udn.dut.algoarena.port.service.ICustomerService;
import vn.udn.dut.algoarena.system.domain.bo.SysUserBo;
import vn.udn.dut.algoarena.system.domain.bo.SysUserProfileBo;
import vn.udn.dut.algoarena.system.domain.vo.AvatarVo;
import vn.udn.dut.algoarena.system.domain.vo.ProfileVo;
import vn.udn.dut.algoarena.system.domain.vo.SysOssVo;
import vn.udn.dut.algoarena.system.domain.vo.SysUserVo;
import vn.udn.dut.algoarena.system.service.ISysOssService;
import vn.udn.dut.algoarena.system.service.ISysUserService;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * Personal Information Business Processing
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController {

    private final ICustomerService customerUserService;
    private final ISysOssService ossService;
    
    /**
     * personal information
     */
    @GetMapping
    public R<CustomerProfileVo> profile() {
        CustomerVo user = customerUserService.selectByUserId(LoginHelper.getUserId());
        CustomerProfileVo profileVo = new CustomerProfileVo();
        profileVo.setUser(user);
        return R.ok(profileVo);
    }

//    /**
//     * modify user
//     */
//    @Log(title = "Personal information", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public R<Void> updateProfile(@RequestBody SysUserProfileBo profile) {
//        SysUserBo user = BeanUtil.toBean(profile, SysUserBo.class);
//        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
//            return R.fail("Edit user '" + user.getUserName() + "' failed, phone number already exists");
//        }
//        if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
//            return R.fail("Edit user '" + user.getUserName() + "' failed, email account already exists");
//        }
//        user.setUserId(LoginHelper.getUserId());
//        if (userService.updateUserProfile(user) > 0) {
//            return R.ok();
//        }
//        return R.fail("There is an exception in modifying personal information, please contact the administrator");
//    }
//
//    /**
//     * reset Password
//     *
//     * @param newPassword Old Password
//     * @param oldPassword New Password
//     */
//    @Log(title = "Personal information", businessType = BusinessType.UPDATE)
//    @PutMapping("/updatePwd")
//    public R<Void> updatePwd(String oldPassword, String newPassword) {
//        SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
//        String password = user.getPassword();
//        if (!BCrypt.checkpw(oldPassword, password)) {
//            return R.fail("Failed to change the password, the old password is wrong");
//        }
//        if (BCrypt.checkpw(newPassword, password)) {
//            return R.fail("The new password cannot be the same as the old password");
//        }
//
//        if (userService.resetUserPwd(user.getUserId(), BCrypt.hashpw(newPassword)) > 0) {
//            return R.ok();
//        }
//        return R.fail("Password modification is abnormal, please contact the administrator");
//    }

    /**
     * Avatar upload
     *
     * @param avatarfile profile picture
     */
    @Log(title = "User avatar", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<AvatarVo> avatar(@RequestPart("avatarfile") MultipartFile avatarfile) {
        if (!avatarfile.isEmpty()) {
            String extension = FileUtil.extName(avatarfile.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return R.fail("The file format is incorrect, please upload " + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + " format");
            }
            SysOssVo oss = ossService.upload(avatarfile);
            String avatar = oss.getUrl();
            if (customerUserService.updateUserAvatar(LoginHelper.getUserId(), oss.getOssId())) {
                AvatarVo avatarVo = new AvatarVo();
                avatarVo.setImgUrl(avatar);
                return R.ok(avatarVo);
            }
        }
        return R.fail("There is an error in uploading pictures, please contact the administrator");
    }
}