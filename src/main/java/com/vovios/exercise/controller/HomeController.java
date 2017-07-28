package com.vovios.exercise.controller;

import java.text.DateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    
    @Data
    public static class Input {
        private String str;
        private ZoneId timezone;
    }
    
    @Data
    public static class Datum {
        private String apple;
        private String str;
    }

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "home";
    }

    @RequestMapping(value = "/api/greeting", method = RequestMethod.GET)
    @ResponseBody public String greeting(String str, @RequestParam("timezone") ZoneId timezone, Input obe, Datum da) {
        System.out.println(obe.getTimezone().getDisplayName(TextStyle.FULL, Locale.KOREA));
        System.out.println(ZonedDateTime.now().withZoneSameInstant(obe.getTimezone()));
        return "Text (" + str + ", " + obe + ", " + da + ", " + timezone + ")";
    }
    
    @PreAuthorize(value = "denyAll")
    @RequestMapping(value = "/api/protected", method = RequestMethod.GET)
    @ResponseBody public String apiProtectedText() {
        return "api Protected Text";
    }
    
    @PreAuthorize(value = "denyAll")
    @RequestMapping(value = "/protected", method = RequestMethod.GET)
    @ResponseBody public String protectedText() {
        return "Protected Text";
    }

}
