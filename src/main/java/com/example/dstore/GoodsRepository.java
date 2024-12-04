package com.example.dstore;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Query("SELECT p FROM Goods p WHERE CONCAT(p.goodtype, '') LIKE %?1%")
    List<Goods> searchGoods(String keyword);

    @Query("SELECT p FROM Goods p WHERE CONCAT(p.goodname, '') LIKE %?1%")
    List<Goods> searchName(String keyword);
}
