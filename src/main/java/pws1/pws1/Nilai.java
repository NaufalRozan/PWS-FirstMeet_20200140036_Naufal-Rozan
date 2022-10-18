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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rozan
 */
@Entity
@Table(name = "nilai")
@NamedQueries({
    @NamedQuery(name = "Nilai.findAll", query = "SELECT n FROM Nilai n"),
    @NamedQuery(name = "Nilai.findByIDNilai", query = "SELECT n FROM Nilai n WHERE n.iDNilai = :iDNilai"),
    @NamedQuery(name = "Nilai.findByKodeMK", query = "SELECT n FROM Nilai n WHERE n.kodeMK = :kodeMK"),
    @NamedQuery(name = "Nilai.findByNilai", query = "SELECT n FROM Nilai n WHERE n.nilai = :nilai")})
public class Nilai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDNilai")
    private String iDNilai;
    @Basic(optional = false)
    @Column(name = "KodeMK")
    private String kodeMK;
    @Basic(optional = false)
    @Column(name = "Nilai")
    private Character nilai;
    @JoinColumn(name = "NomorIndukMahasiswa", referencedColumnName = "NomorIndukMahasiswa")
    @OneToOne(optional = false)
    private Mahasiswa nomorIndukMahasiswa;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "nilai")
    private Matakuliah matakuliah;

    public Nilai() {
    }

    public Nilai(String iDNilai) {
        this.iDNilai = iDNilai;
    }

    public Nilai(String iDNilai, String kodeMK, Character nilai) {
        this.iDNilai = iDNilai;
        this.kodeMK = kodeMK;
        this.nilai = nilai;
    }

    public String getIDNilai() {
        return iDNilai;
    }

    public void setIDNilai(String iDNilai) {
        this.iDNilai = iDNilai;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public void setKodeMK(String kodeMK) {
        this.kodeMK = kodeMK;
    }

    public Character getNilai() {
        return nilai;
    }

    public void setNilai(Character nilai) {
        this.nilai = nilai;
    }

    public Mahasiswa getNomorIndukMahasiswa() {
        return nomorIndukMahasiswa;
    }

    public void setNomorIndukMahasiswa(Mahasiswa nomorIndukMahasiswa) {
        this.nomorIndukMahasiswa = nomorIndukMahasiswa;
    }

    public Matakuliah getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(Matakuliah matakuliah) {
        this.matakuliah = matakuliah;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDNilai != null ? iDNilai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nilai)) {
            return false;
        }
        Nilai other = (Nilai) object;
        if ((this.iDNilai == null && other.iDNilai != null) || (this.iDNilai != null && !this.iDNilai.equals(other.iDNilai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pws1.pws1.Nilai[ iDNilai=" + iDNilai + " ]";
    }
    
}
