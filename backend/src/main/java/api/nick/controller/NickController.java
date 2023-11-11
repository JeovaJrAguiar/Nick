package api.nick.controller;

import api.nick.entity.dataset.ResponseDTO;
import api.nick.service.NickBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/nick")
public class NickController {
    @Autowired
    private NickBotService nickBotService;

    @GetMapping("/question")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'ADMIN')")
    public ResponseEntity<ResponseDTO> getInformation(@RequestParam("question") String question) throws Exception{
        try {
            String response = NickBotService.classificarPergunta(question);
            return new ResponseEntity<>(new ResponseDTO(response), HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
