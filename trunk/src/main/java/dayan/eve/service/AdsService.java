package dayan.eve.service;

import dayan.eve.model.Advertisement;
import dayan.eve.model.account.AccountInfo;
import dayan.eve.model.query.AdsQuery;
import dayan.eve.repository.AccountInfoRepository;
import dayan.eve.repository.AdsRepository;
import dayan.eve.util.ImageBaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by xsg on 6/3/2017.
 */
@Service
public class AdsService {
    @Autowired
    AdsRepository adsRepository;
    @Autowired
    AccountInfoRepository accountInfoRepository;

    public Advertisement readAds(AdsQuery query) {
        Integer accountId = query.getAccountId();
        if (accountId != null) {
            AccountInfo accountInfo = accountInfoRepository.queryOneInfo(accountId);
            if (accountInfo != null) {
                query.setSubjectTypeId("文科".equals(accountInfo.getSubjectType()) ? 1 : 2);
                query.setProvinceId(accountInfo.getProvinceId());
                query.setScore(accountInfo.getScore());
            }
        }
        Advertisement ads = adsRepository.queryRandomOne(query);
        if (ads == null) {
            return null;
        }
        adsRepository.updateReadTimes(ads.getId());
        return ads;
    }

    //todo 后台功能
    @Value("${image.ads.create.url.prefix}")
    String IMAGE_ADS_CREATE_PREFIX;
    @Value("${image.ads.read.url.prefix}")
    String IMAGE_ADS_READ_PREFIX;

    public String uploadImage(MultipartFile file) {
        return ImageBaseUtil.uploadSingleImage(file, IMAGE_ADS_CREATE_PREFIX, IMAGE_ADS_READ_PREFIX);
    }

    public void createAds(Advertisement ad) {
        adsRepository.insert(ad);
    }

    public void updateAds(Advertisement ad) {
        adsRepository.update(ad);
    }

    public Advertisement readStartPage() {
        AdsQuery adsQuery = new AdsQuery();
        adsQuery.setIsStartpage(true);
        return adsRepository.query(adsQuery).get(0);
    }

    public void delete(Integer id) {
        adsRepository.delete(id);
    }

    public List<Advertisement> readAdses(Boolean isStartpage) {
        AdsQuery adsQuery = new AdsQuery();
        adsQuery.setIsStartpage(isStartpage);
        return adsRepository.query(adsQuery);
    }
}
