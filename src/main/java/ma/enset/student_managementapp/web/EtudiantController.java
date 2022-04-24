package ma.enset.student_managementapp.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.enset.student_managementapp.entities.Etudiant;
import ma.enset.student_managementapp.repositories.EtudiantRepository;
import ma.enset.student_managementapp.security.repositories.AppRoleRepository;
import ma.enset.student_managementapp.security.repositories.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
@Controller
@Data @AllArgsConstructor
public class EtudiantController {
    private EtudiantRepository etudiantRepository;
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           //param d'url
                           @RequestParam(name="page", defaultValue = "0") int page,
                           @RequestParam(name="size", defaultValue = "5") int size,
                           @RequestParam(name="keyword", defaultValue = "") String keyword){
        Page<Etudiant> pageEtudiant = etudiantRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listEtudiant",pageEtudiant.getContent());
        model.addAttribute("pages",new int[pageEtudiant.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "etudiants";
    }
    @GetMapping("/admin/delete")
    public String delete(Long id, String keyword, int page){
        etudiantRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/user/etudiants")
    @ResponseBody
    public List<Etudiant> listPatients(){
        return etudiantRepository.findAll();
    }

    @GetMapping("/admin/formEtudiant")
    public String formEtudiant(Model model){
        String keyword ="";
        int page = 0;
        model.addAttribute("etudiant",new Etudiant());
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "formEtudiant";
    }

    @PostMapping("/admin/save")
    public String save(Model model, @Valid Etudiant etudiant, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "")String keyword){
        if (bindingResult.hasErrors())
            return "formEtudiant";
        etudiantRepository.save(etudiant);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/editEtudiant")
    public String editPatient(Model model,Long id,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "0") int page){
        Etudiant etudiant = etudiantRepository.findById(id).orElse(null);
        if (etudiant==null) throw new RuntimeException("Etudiant introuvable!!!");
        model.addAttribute("etudiant",etudiant);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editEtudiant";
    }

    @GetMapping("/user/listEtudiant")
    public String listPatient(Model model, Long id,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "0") int page){
        Etudiant etudiant = etudiantRepository.findById(id).get();
        model.addAttribute("etudiant",etudiant);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "listEtudiant";
    }
}
