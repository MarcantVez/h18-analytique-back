package com.squidsquads.repository.visit;

import com.squidsquads.model.traffic.TrackingInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingInfoRepository extends CrudRepository<TrackingInfo, Long> {
    List<TrackingInfo> findAllByFingerprint(String fingerprint);
    TrackingInfo findFirstByFingerprintOrderByDateTimeDesc (String fingerprint);
}
