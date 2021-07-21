package com.nvfredy.testapp.controller;

import com.nvfredy.testapp.entity.History;
import com.nvfredy.testapp.entity.Security;
import com.nvfredy.testapp.service.HistoryService;
import com.nvfredy.testapp.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

@Controller
@Slf4j
public class MainController {

        private final SecurityService securityService;
    private final HistoryService historyService;

    public MainController(SecurityService securityService, HistoryService historyService) {
        this.securityService = securityService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String indexPage() {
        return "main";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                if (file.getOriginalFilename().startsWith("history_")) {
                    File his = new File("src/main/resources/xml/histories/" + file.getOriginalFilename());
                    BufferedOutputStream out =
                            new BufferedOutputStream(new FileOutputStream(his));
                    out.write(bytes);
                    out.close();
                    historyService.saveAll(his);
                } else if (file.getOriginalFilename().startsWith("securities_")) {
                    File sec = new File("src/main/resources/xml/securities/" + file.getOriginalFilename());
                    BufferedOutputStream out =
                            new BufferedOutputStream(new FileOutputStream(sec));
                    out.write(bytes);
                    out.close();
                    securityService.saveAll(sec);
                }

                log.info(String.format("File %s upload successfully.", file.getOriginalFilename()));

            } catch (IOException e) {
                log.error("Error with upload file!");
            }
        }
        return "redirect:/";
    }

    @GetMapping("/histories")
    public String getAllHistories(Model model) {
        List<History> list = historyService.findAll();
        model.addAttribute("histories", list);
        return "histories";
    }

    @GetMapping("/securities")
    public String getAllSecurities(Model model) {
        List<Security> list = securityService.findAll();
        model.addAttribute("securities", list);
        return "securities";
    }

    @PostMapping("/delete/{secId}")
    @Transactional
    public String deleteHistory(@PathVariable String secId) {
        historyService.deleteById(secId);
        return "redirect:/histories";
    }
}
