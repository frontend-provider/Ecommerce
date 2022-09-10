package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPham> getDsSanPham() {
        log.info("Fetching all san_pham");
        return sanPhamRepository.findAll();
    }

    @Override
    public List<SanPham> getDsSanPham(String phanLoai) {
        log.info("Fetching all san_pham by phan_loai {}", phanLoai);
        var result = sanPhamRepository.findByPhanLoai(phanLoai);
        if (result.size() == 0) {
            result = sanPhamRepository.findAll();
        }
        return result.stream().filter(sanPham -> sanPham.getTonKho() > 0).collect(toList());
    }

    @Override
    public SanPham getSanPham(int id) {
        log.info("Fetching san_pham with id {}", id);
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public void saveSanPham(SanPham sanPham) {
        log.info("Saving san_pham with name: {}", sanPham.getTen());
        sanPhamRepository.save(sanPham);
    }

    @Override
    public void deleteSanPham(int id) {
        log.info("Deleting san_pham with id: {}", id);
        sanPhamRepository.deleteById(id);
    }

    @Override
    public List<SanPham> getDsSanPhamTonKho() {
        log.info("Fetching all san_pham ton_kho");
        return sanPhamRepository.findAll().stream().filter(sanPham -> sanPham.getTonKho() > 0).collect(toList());
    }

    @Override
    public List<SanPham> getDsSanPhamTopSale() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getTonKho() < firstProduct.getTonKho() ? 1 : -1;
        });
        log.info("Fetching san_pham with top sale");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamNewest() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getNgayNhap().compareTo(firstProduct.getNgayNhap());
        });
        log.info("Fetching san_pham with newest");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamAscendingPrice() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            var firstGiaBan = firstProduct.getGiaGoc() - firstProduct.getKhuyenMai();
            var secondGiaBan = secondProduct.getGiaGoc() - secondProduct.getKhuyenMai();
            return secondGiaBan < firstGiaBan ? 1 : -1;
        });
        log.info("Fetching san_pham with ascending price");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamDescendingPrice() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            var firstGiaBan = firstProduct.getGiaGoc() - firstProduct.getKhuyenMai();
            var secondGiaBan = secondProduct.getGiaGoc() - secondProduct.getKhuyenMai();
            return secondGiaBan > firstGiaBan ? 1 : -1;
        });
        log.info("Fetching san_pham with ascending price");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamAscendingDiscount() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getKhuyenMai() < firstProduct.getKhuyenMai() ? 1 : -1;
        });
        log.info("Fetching san_pham with ascending discount");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamDescendingDiscount() {
        var dsSanPham = getDsSanPhamTonKho();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getKhuyenMai() > firstProduct.getKhuyenMai() ? 1 : -1;
        });
        log.info("Fetching san_pham with descending discount");
        return dsSanPham;
    }
}
