package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultFactory;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    public String createOrUpdateCredential(
        @ModelAttribute Credential credential,
        RedirectAttributes redirectAttributes
    ) {
        this.credentialService.createOrUpdateCredential(credential);
        redirectAttributes.addFlashAttribute(
            "result",
            ResultFactory.createSuccessResult(
                "Credential successfully created or updated."
            )
        );
        return "redirect:/result";
    }

    @GetMapping("/delete/{credentialIdString}")
    public String deleteFileByFileId(
        @PathVariable String credentialIdString,
        RedirectAttributes redirectAttributes
    ) {
        Integer credentialId = Integer.parseInt(credentialIdString);

        this.credentialService.deleteCredentialByCredentialId(credentialId);

        redirectAttributes.addFlashAttribute(
            "result",
            ResultFactory.createSuccessResult("Credential deleted successfully.")
        );
        return "redirect:/result";
    }
}
