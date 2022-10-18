/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pws1.pws1;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pws1.pws1.exceptions.IllegalOrphanException;
import pws1.pws1.exceptions.NonexistentEntityException;
import pws1.pws1.exceptions.PreexistingEntityException;

/**
 *
 * @author rozan
 */
public class NilaiJpaController implements Serializable {

    public NilaiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pws1_pws1_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public NilaiJpaController() {
    }
    
    

    public void create(Nilai nilai) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Mahasiswa nomorIndukMahasiswaOrphanCheck = nilai.getNomorIndukMahasiswa();
        if (nomorIndukMahasiswaOrphanCheck != null) {
            Nilai oldNilai1OfNomorIndukMahasiswa = nomorIndukMahasiswaOrphanCheck.getNilai1();
            if (oldNilai1OfNomorIndukMahasiswa != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Mahasiswa " + nomorIndukMahasiswaOrphanCheck + " already has an item of type Nilai whose nomorIndukMahasiswa column cannot be null. Please make another selection for the nomorIndukMahasiswa field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mahasiswa nomorIndukMahasiswa = nilai.getNomorIndukMahasiswa();
            if (nomorIndukMahasiswa != null) {
                nomorIndukMahasiswa = em.getReference(nomorIndukMahasiswa.getClass(), nomorIndukMahasiswa.getNomorIndukMahasiswa());
                nilai.setNomorIndukMahasiswa(nomorIndukMahasiswa);
            }
            Matakuliah matakuliah = nilai.getMatakuliah();
            if (matakuliah != null) {
                matakuliah = em.getReference(matakuliah.getClass(), matakuliah.getKodeMK());
                nilai.setMatakuliah(matakuliah);
            }
            em.persist(nilai);
            if (nomorIndukMahasiswa != null) {
                nomorIndukMahasiswa.setNilai1(nilai);
                nomorIndukMahasiswa = em.merge(nomorIndukMahasiswa);
            }
            if (matakuliah != null) {
                Nilai oldNilaiOfMatakuliah = matakuliah.getNilai();
                if (oldNilaiOfMatakuliah != null) {
                    oldNilaiOfMatakuliah.setMatakuliah(null);
                    oldNilaiOfMatakuliah = em.merge(oldNilaiOfMatakuliah);
                }
                matakuliah.setNilai(nilai);
                matakuliah = em.merge(matakuliah);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNilai(nilai.getIDNilai()) != null) {
                throw new PreexistingEntityException("Nilai " + nilai + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nilai nilai) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nilai persistentNilai = em.find(Nilai.class, nilai.getIDNilai());
            Mahasiswa nomorIndukMahasiswaOld = persistentNilai.getNomorIndukMahasiswa();
            Mahasiswa nomorIndukMahasiswaNew = nilai.getNomorIndukMahasiswa();
            Matakuliah matakuliahOld = persistentNilai.getMatakuliah();
            Matakuliah matakuliahNew = nilai.getMatakuliah();
            List<String> illegalOrphanMessages = null;
            if (nomorIndukMahasiswaNew != null && !nomorIndukMahasiswaNew.equals(nomorIndukMahasiswaOld)) {
                Nilai oldNilai1OfNomorIndukMahasiswa = nomorIndukMahasiswaNew.getNilai1();
                if (oldNilai1OfNomorIndukMahasiswa != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Mahasiswa " + nomorIndukMahasiswaNew + " already has an item of type Nilai whose nomorIndukMahasiswa column cannot be null. Please make another selection for the nomorIndukMahasiswa field.");
                }
            }
            if (matakuliahOld != null && !matakuliahOld.equals(matakuliahNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Matakuliah " + matakuliahOld + " since its nilai field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (nomorIndukMahasiswaNew != null) {
                nomorIndukMahasiswaNew = em.getReference(nomorIndukMahasiswaNew.getClass(), nomorIndukMahasiswaNew.getNomorIndukMahasiswa());
                nilai.setNomorIndukMahasiswa(nomorIndukMahasiswaNew);
            }
            if (matakuliahNew != null) {
                matakuliahNew = em.getReference(matakuliahNew.getClass(), matakuliahNew.getKodeMK());
                nilai.setMatakuliah(matakuliahNew);
            }
            nilai = em.merge(nilai);
            if (nomorIndukMahasiswaOld != null && !nomorIndukMahasiswaOld.equals(nomorIndukMahasiswaNew)) {
                nomorIndukMahasiswaOld.setNilai1(null);
                nomorIndukMahasiswaOld = em.merge(nomorIndukMahasiswaOld);
            }
            if (nomorIndukMahasiswaNew != null && !nomorIndukMahasiswaNew.equals(nomorIndukMahasiswaOld)) {
                nomorIndukMahasiswaNew.setNilai1(nilai);
                nomorIndukMahasiswaNew = em.merge(nomorIndukMahasiswaNew);
            }
            if (matakuliahNew != null && !matakuliahNew.equals(matakuliahOld)) {
                Nilai oldNilaiOfMatakuliah = matakuliahNew.getNilai();
                if (oldNilaiOfMatakuliah != null) {
                    oldNilaiOfMatakuliah.setMatakuliah(null);
                    oldNilaiOfMatakuliah = em.merge(oldNilaiOfMatakuliah);
                }
                matakuliahNew.setNilai(nilai);
                matakuliahNew = em.merge(matakuliahNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = nilai.getIDNilai();
                if (findNilai(id) == null) {
                    throw new NonexistentEntityException("The nilai with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nilai nilai;
            try {
                nilai = em.getReference(Nilai.class, id);
                nilai.getIDNilai();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nilai with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Matakuliah matakuliahOrphanCheck = nilai.getMatakuliah();
            if (matakuliahOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Nilai (" + nilai + ") cannot be destroyed since the Matakuliah " + matakuliahOrphanCheck + " in its matakuliah field has a non-nullable nilai field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Mahasiswa nomorIndukMahasiswa = nilai.getNomorIndukMahasiswa();
            if (nomorIndukMahasiswa != null) {
                nomorIndukMahasiswa.setNilai1(null);
                nomorIndukMahasiswa = em.merge(nomorIndukMahasiswa);
            }
            em.remove(nilai);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nilai> findNilaiEntities() {
        return findNilaiEntities(true, -1, -1);
    }

    public List<Nilai> findNilaiEntities(int maxResults, int firstResult) {
        return findNilaiEntities(false, maxResults, firstResult);
    }

    private List<Nilai> findNilaiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nilai.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Nilai findNilai(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nilai.class, id);
        } finally {
            em.close();
        }
    }

    public int getNilaiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nilai> rt = cq.from(Nilai.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
