package com.studycollaboproject.scope.ipfilter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@Setter
@RequiredArgsConstructor
public class IpBanService {
    private final IpBanRepository ipBanRepository;

    public String getIpAdress(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");


        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        log.info("[{}],Result : IP Address : {}", MDC.get("UUID"),ip);

        return ip;
    }

    public boolean isIpBanned(HttpServletRequest request){
        String ipAddress = getIpAdress(request);
        return ipBanRepository.findIpBanListByIp(ipAddress).isPresent();
    }

    @Transactional
    public void saveBanIp(String ipAddress){
        if (ipBanRepository.findIpBanListByIp(ipAddress).isEmpty()){
            IpBanList ipBanList = new IpBanList(ipAddress);
            ipBanRepository.save(ipBanList);
        }
    }
}
