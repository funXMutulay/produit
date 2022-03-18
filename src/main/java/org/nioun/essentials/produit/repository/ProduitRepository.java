package org.nioun.essentials.produit.repository;

import org.nioun.essentials.produit.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "produit" , path="produit")
public interface ProduitRepository extends JpaRepository<Produit,Long> {
	
	Produit findByNameAndDescription(String name , String description);

}
