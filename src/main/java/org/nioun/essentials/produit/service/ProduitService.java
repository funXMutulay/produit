package org.nioun.essentials.produit.service;

import java.math.BigDecimal;
import java.util.List;

import org.nioun.essentials.produit.model.Produit;
import org.springframework.web.multipart.MultipartFile;


public interface ProduitService {

	Produit saveProduit(Produit produit);
	
	void saveProduitToDB(MultipartFile file ,String name , String description ,BigDecimal price, String env);
	
	void updateProduitToDB(Long produitId , MultipartFile file ,String name , String description ,BigDecimal price, String env);
	
	Produit updateProduit(Produit produit);
	
	void deleteProduit(Produit produit);

	void deleteProduitById(Long produitId);
	
	Produit getProduit(Long produitId);
	
	List<Produit> getAllProduits();
	
	void changeProduitImage(Long produitId,MultipartFile file);
}
