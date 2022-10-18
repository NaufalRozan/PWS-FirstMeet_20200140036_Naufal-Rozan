/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pws1.pws1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rozan
 */
@Entity
@Table(name = "mahasiswa")
@NamedQueries({
    @NamedQuery(name = "Mahasiswa.findAll", query = "SELECT m FROM Mahasiswa m"),
    @NamedQuery(name = "Mahasiswa.findByNomorIndukMahasiswa", query = "SELECT m FROM Mahasiswa m WHERE m.nomorIndukMahasiswa = :nomorIndukMahasiswa"),
    @NamedQuery(name = "Mahasiswa.findByMataKuliah", query = "SELECT m FROM Mahasiswa m WHERE m.mataKuliah = :mataKuliah"),
    @NamedQuery(name = "Mahasiswa.findByNilai", query = "SELECT m FROM Mahasiswa m WHERE m.nilai = :nilai")})
public class Mahasiswa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NomorIndukMahasiswa")
    private String nomorIndukMahasiswa;
    @Basic(optional = false)
    @Column(name = "MataKuliah")
    private String mataKuliah;
    @Basic(optional = false)
    @Column(name = "Nilai")
    private String nilai;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "nomorIndukMahasiswa")
    private Nilai nilai1;

    public Mahasiswa() {
    }

    public Mahasiswa(String nomorIndukMahasiswa) {
        this.nomorIndukMahasiswa = nomorIndukMahasiswa;
    }

    public Mahasiswa(String nomorIndukMahasiswa, String mataKuliah, String nilai) {
        this.nomorIndukMahasiswa = nomorIndukMahasiswa;
        this.mataKuliah = mataKuliah;
        this.nilai = nilai;
    }

    public String getNomorIndukMahasiswa() {
        return nomorIndukMahasiswa;
    }

    public void setNomorIndukMahasiswa(String nomorIndukMahasiswa) {
        this.nomorIndukMahasiswa = nomorIndukMahasiswa;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public Nilai getNilai1() {
        return nilai1;
    }

    public void setNilai1(Nilai nilai1) {
        this.nilai1 = nilai1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomorIndukMahasiswa != null ? nomorIndukMahasiswa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mahasiswa)) {
            return false;
        }
        Mahasiswa other = (Mahasiswa) object;
        if ((this.nomorIndukMahasiswa == null && other.nomorIndukMahasiswa != null) || (this.nomorIndukMahasiswa != null && !this.nomorIndukMahasiswa.equals(other.nomorIndukMahasiswa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pws1.pws1.Mahasiswa[ nomorIndukMahasiswa=" + nomorIndukMahasiswa + " ]";
    }
    
}
