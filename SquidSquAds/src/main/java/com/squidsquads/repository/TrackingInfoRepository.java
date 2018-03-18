package com.squidsquads.repository;

import com.squidsquads.model.TrackingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackingInfoRepository extends JpaRepository<TrackingInfo, Integer> {

    List<TrackingInfo> findAllByFingerprint(UUID fingerprint);

    TrackingInfo findFirstByFingerprintOrderByDateTimeDesc(UUID fingerprint);

    List<TrackingInfo> findAllByFingerprintAndCurrentUrl(UUID fingerprint, String currentUrl);
}
