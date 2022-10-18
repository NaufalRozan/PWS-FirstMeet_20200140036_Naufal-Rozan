/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pws1.pws1;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "matakuliah")
@NamedQueries({
    @NamedQuery(name = "Matakuliah.findAll", query = "SELECT m FROM Matakuliah m"),
    @NamedQuery(name = "Matakuliah.findByKodeMK", query = "SELECT m FROM Matakuliah m WHERE m.kodeMK = :kodeMK"),
    @NamedQuery(name = "Matakuliah.findByNamaMK", query = "SELECT m FROM Matakuliah m WHERE m.namaMK = :namaMK"),
    @NamedQuery(name = "Matakuliah.findBySemester", query = "SELECT m FROM Matakuliah m WHERE m.semester = :semester"),
    @NamedQuery(name = "Matakuliah.findBySks", query = "SELECT m FROM Matakuliah m WHERE m.sks = :sks")})
public class Matakuliah implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "KodeMK")
    private String kodeMK;
    @Basic(optional = false)
    @Column(name = "NamaMK")
    private String namaMK;
    @Basic(optional = false)
    @Column(name = "Semester")
    private int semester;
    @Basic(optional = false)
    @Column(name = "SKS")
    private int sks;
    @JoinColumn(name = "KodeMK", referencedColumnName = "KodeMK", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Nilai nilai;

    public Matakuliah() {
    }

    public Matakuliah(String kodeMK) {
        this.kodeMK = kodeMK;
    }

    public Matakuliah(String kodeMK, String namaMK, int semester, int sks) {
        this.kodeMK = kodeMK;
        this.namaMK = namaMK;
        this.semester = semester;
        this.sks = sks;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public void setKodeMK(String kodeMK) {
        this.kodeMK = kodeMK;
    }

    public String getNamaMK() {
        return namaMK;
    }

    public void setNamaMK(String namaMK) {
        this.namaMK = namaMK;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public Nilai getNilai() {
        return nilai;
    }

    public void setNilai(Nilai nilai) {
        this.nilai = nilai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeMK != null ? kodeMK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matakuliah)) {
            return false;
        }
        Matakuliah other = (Matakuliah) object;
        if ((this.kodeMK == null && other.kodeMK != null) || (this.kodeMK != null && !this.kodeMK.equals(other.kodeMK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pws1.pws1.Matakuliah[ kodeMK=" + kodeMK + " ]";
    }
    
}
