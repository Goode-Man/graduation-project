package com.nit.hjh.captcha.controller;

import com.google.code.kaptcha.Constants;
import com.hjh.nit.common.BaseController;
import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.api.service.CaptchaFeignApiFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.code.kaptcha.Producer;

import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/captcha")
public class CaptchaController extends BaseController implements CaptchaFeignApiFeign {

    @Autowired
    private Producer producer;

    @GetMapping("/crateImg")
    public Wrapper<String> crateImg(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        try {
            if (out != null) {
                out.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
        return handleResult(text);
    }

    @Override
    @GetMapping("/crateWords")
    public Wrapper<String> crateWords() {
        // 生成文字验证码
        String text = producer.createText();
//        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        return handleResult(text);
    }
}
