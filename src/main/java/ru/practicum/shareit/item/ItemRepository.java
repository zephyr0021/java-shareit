package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<ItemShort> findItemById(Long id);
    List<ItemShort> findItemsByOwner_Id(Long id);

    @Query("SELECT i FROM Item i " +
            "WHERE (LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND i.available = true")
    List<ItemShort> searchItemsByQuery(@Param("query") String query);
}
