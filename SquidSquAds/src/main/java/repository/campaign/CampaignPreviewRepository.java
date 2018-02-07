package repository.campaign;

import model.dto.CampaignPreview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignPreviewRepository extends JpaRepository<CampaignPreview, Long> {
    @Query("SELECT new CampaignPreview(c.numero_campagne, c.nom, to_char(c.date_creation, \"YYYY-MM-DD\")) FROM campagne c WHERE numero_compte = :compte")
    List<CampaignPreview> findAllByAccountId(@Param("compte") Long accountId);
}
