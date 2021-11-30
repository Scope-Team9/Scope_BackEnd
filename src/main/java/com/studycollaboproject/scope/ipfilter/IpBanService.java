package com.studycollaboproject.scope.ipfilter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Slf4j
@Service
@Setter
@RequiredArgsConstructor
public class IpBanService {
    private final IpBanRepository ipBanRepository;

    public String getIpAdress(HttpServletRequest request){
        String ip = request.getHeader("X-Fowarded-For");


        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info(">  WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            log.info("> getRemoteAddr : "+ip);
        }
        log.info("> Result : IP Address : "+ip);

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
