package com.squidsquads.repository;

import com.squidsquads.model.TrackingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingInfoRepository extends JpaRepository<TrackingInfo, Integer> {

    List<TrackingInfo> findAllByFingerprint(String fingerprint);

    TrackingInfo findFirstByFingerprintOrderByDateTimeDesc(String fingerprint);
}
