package com.squidsquads.repository.visit;

import com.squidsquads.model.traffic.TrackingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingInfoRepository extends JpaRepository<TrackingInfo, Long> {

    List<TrackingInfo> findAllByFingerprint(String fingerprint);

    TrackingInfo findFirstByFingerprintOrderByDateTimeDesc(String fingerprint);
}
