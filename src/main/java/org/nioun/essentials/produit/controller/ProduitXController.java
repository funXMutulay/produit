package org.nioun.essentials.produit.controller;

import java.math.BigDecimal;

import org.nioun.essentials.produit.model.Produit;
import org.nioun.essentials.produit.repository.ProduitRepository;
import org.nioun.essentials.produit.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProduitXController {
	@Autowired
	ProduitService service;

	@Autowired
	ProduitRepository repository;
  
	
	@GetMapping("/produit/index" )
	public String index(Model model) {
	    model.addAttribute("produits", repository.findAll());
	    return "index";
	}
	
	
	@GetMapping("/produit/creerProduit")
    public String creerProduit(Produit produit) {
        return "ajouterProduit";
    }
    
	 @PostMapping("/saveProduitToDB")
	    public String saveProduitToDB(@RequestParam("file") MultipartFile file ,@RequestParam("name") String name ,
	    		@RequestParam("description") String description ,@RequestParam("price") BigDecimal price,
	    		@RequestParam("env")  String env) {
	                
	        service.saveProduitToDB(file, name, description, price, env);
	        return "redirect:/produit/creerProduit";
	    }

	
    @GetMapping("/showUpdate/{produitId}")
    public String showUpdate(@PathVariable("produitId") long produitId, Model model) {
        Produit produit= repository.findById(produitId)
          .orElseThrow(() -> new IllegalArgumentException("Invalid  produitId:" + produitId));
        
        model.addAttribute("produit", produit);
        return "updateProduit";
    }
    
    
    @PostMapping("/updateProduit/{produitId}")
	public String updateProduit(@PathVariable("produitId") long produitId, Produit produit, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	    	produit.setProduitId(produitId);
	        return "updateProduit";
	    }
	        
	    service.saveProduit(produit);
	    return "redirect:/produit/index";
	}
	
    @PostMapping("/updateProduitToDB")
    public String updateProduitToDB(@RequestParam("produitId") Long produitId ,
    		@RequestParam("file") MultipartFile file ,
    		@RequestParam("name") String name ,
    		@RequestParam("description") String description ,
    		@RequestParam("price") BigDecimal price,
    		@RequestParam("env")  String env ,
    		Model model) {
       
    	service.updateProduitToDB(produitId , file , name, description, price, env);
        model.addAttribute("produits", repository.findAll());
        
        return "redirect:/produit/index";
    }
    
    @GetMapping("/delete/{produitId}")
	public String deleteProduit(@PathVariable("produitId") long produitId, Model model) {
		
    	/*
    	Produit produit = repository.findById(produitId)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user produitId:" + produitId));
	    repository.delete(produit);
	    */
    	
    	 service.deleteProduitById(produitId);
    	model.addAttribute("produits" , repository.findAll());
	    return "redirect:/index";
	}

    
    @PostMapping("/changeProduitImage")
    public String changeProduitImage(@RequestParam("produitId") Long produitId,
    		@RequestParam("nfile") MultipartFile nfile)
    {
    	service.changeProduitImage(produitId, nfile);
    	return "redirect:/listProducts.html";
    }
	
}
