package com.example.demo;

import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Id;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    SkillRepository skillRepository;


    @RequestMapping("/")
    public String myhomepage(Model model){
        model.addAttribute("resumes", resumeRepository.findAll());
        return "hometemplate";
    }

    @GetMapping("/addresume")
    public String addResumeForm(Model model){
        model.addAttribute("resume", new Resume());
        return "resumeform";
    }

    @GetMapping("/addskill/{id}")
    public String addSkillForm(@PathVariable("id") long id, Model model){
        Skill skill = new Skill();
        skill.setResumeid(id);
        model.addAttribute("skill", new Skill());
        return "skill";
    }

    @PostMapping("/saveSkill")
    public String saveSkill(Model model, @Valid Skill skill, BindingResult result) {
        if (result.hasErrors()) {
            return "skill";
        }
        skillRepository.save(skill);
        Resume resume = resumeRepository.findById(skill.getResumeid()).get();
        model.addAttribute("resume", resume);
        return "resumeform";
    }

    @RequestMapping("/detail/{id}")
    public String getResume(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("resume", resumeRepository.findById(id).get());
        return "view";
    }

    @PostMapping("/processresume")
    public String myresume(@Valid Resume resume, BindingResult result, Model model){
        if(result.hasErrors())
        {
            return "resumeform";
        }
        resumeRepository.save(resume);
        model.addAttribute("resumes", resumeRepository.findAll());
        return "hometemplate";
    }
}
