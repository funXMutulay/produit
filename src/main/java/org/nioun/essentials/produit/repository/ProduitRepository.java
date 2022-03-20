package org.nioun.essentials.produit.repository;

import org.nioun.essentials.produit.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {
	
	Produit findByNameAndDescription(String name , String description);

}
