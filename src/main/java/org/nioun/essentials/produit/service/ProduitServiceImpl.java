package org.nioun.essentials.produit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import org.nioun.essentials.produit.model.Produit;
import org.nioun.essentials.produit.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class ProduitServiceImpl implements ProduitService {

	@Autowired
	ProduitRepository produitRepository; 
	
	
	public ProduitRepository getProduitRepository() {
		return produitRepository;
	}

	public void setProduitRepository(ProduitRepository produitRepository) {
		this.produitRepository = produitRepository;
	}

	public Produit saveProduit(Produit produit) {

		return produitRepository.save(produit);
	}


	public Produit updateProduit(Produit produit) {

		return produitRepository.save(produit);
	}


	public void deleteProduit(Produit produit) {
		produitRepository.delete(produit);

	}


	public Produit getProduit(Long produitId) {

		return produitRepository.findById(produitId).get();
	}


	public List<Produit> getAllProduits() {

		return produitRepository.findAll();
	}

	@Override
	public  void saveProduitToDB(MultipartFile file, String name, String description, BigDecimal price, String env) {
		Produit produit = new Produit();
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains("..")) {
			System.out.println("Fichier invalide");
		}
		
		try {
			produit.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		produit.setName(name);
		produit.setDescription(description);
		produit.setPrice(price);
		produit.setEnv(env);
		produitRepository.save(produit);
		
	}

	@Override
	public void changeProduitImage(Long produitId, MultipartFile file) {
		Produit produit = new Produit();
			produit=produitRepository.findById(produitId).get();
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		if(filename.contains("..")) {
			System.out.println("fichier introuvable");
		}
		try {
		produit.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		}catch(IOException e) {e.printStackTrace();}
		produitRepository.save(produit);
	}

	@Override
	public void updateProduitToDB(Long produitId, MultipartFile file, String name, String description, BigDecimal price,
			String env) {
Produit produit = new Produit();
produit.setProduitId(produitId);
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains("..")) {
			System.out.println("Fichier invalide");
		}
		
		try {
			produit.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		produit.setName(name);
		produit.setDescription(description);
		produit.setPrice(price);
		produit.setEnv(env);
		produitRepository.save(produit);
		
	}

	@Override
	public void deleteProduitById(Long produitId) {
			Produit produit = produitRepository.findById(produitId).get();
			produitRepository.delete(produit);
		
	}
	}
