package com.studycollaboproject.scope.ipfilter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpBanRepository extends JpaRepository<IpBanList,String > {

    Optional<IpBanList> findIpBanListByIp(String ip);
}
