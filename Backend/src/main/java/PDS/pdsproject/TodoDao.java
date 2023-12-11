package PDS.pdsproject;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import PDS.pdsproject.*;

import java.util.List;

@Repository
public interface TodoDao extends JpaRepository<Todo, Integer> {

    @Transactional
    List<Todo> findAll();

    @Transactional
    List<Todo> findById(int id);

    @Transactional
    List<Todo> findByTitle(String title);

    @Transactional
    void deleteByTitle(String title);

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void deleteAll();
}