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
import pws1.pws1.exceptions.IllegalOrphanException;
import pws1.pws1.exceptions.NonexistentEntityException;
import pws1.pws1.exceptions.PreexistingEntityException;

/**
 *
 * @author rozan
 */
public class MahasiswaJpaController implements Serializable {

    public MahasiswaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mahasiswa mahasiswa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nilai nilai1 = mahasiswa.getNilai1();
            if (nilai1 != null) {
                nilai1 = em.getReference(nilai1.getClass(), nilai1.getIDNilai());
                mahasiswa.setNilai1(nilai1);
            }
            em.persist(mahasiswa);
            if (nilai1 != null) {
                Mahasiswa oldNomorIndukMahasiswaOfNilai1 = nilai1.getNomorIndukMahasiswa();
                if (oldNomorIndukMahasiswaOfNilai1 != null) {
                    oldNomorIndukMahasiswaOfNilai1.setNilai1(null);
                    oldNomorIndukMahasiswaOfNilai1 = em.merge(oldNomorIndukMahasiswaOfNilai1);
                }
                nilai1.setNomorIndukMahasiswa(mahasiswa);
                nilai1 = em.merge(nilai1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMahasiswa(mahasiswa.getNomorIndukMahasiswa()) != null) {
                throw new PreexistingEntityException("Mahasiswa " + mahasiswa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mahasiswa mahasiswa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mahasiswa persistentMahasiswa = em.find(Mahasiswa.class, mahasiswa.getNomorIndukMahasiswa());
            Nilai nilai1Old = persistentMahasiswa.getNilai1();
            Nilai nilai1New = mahasiswa.getNilai1();
            List<String> illegalOrphanMessages = null;
            if (nilai1Old != null && !nilai1Old.equals(nilai1New)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Nilai " + nilai1Old + " since its nomorIndukMahasiswa field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (nilai1New != null) {
                nilai1New = em.getReference(nilai1New.getClass(), nilai1New.getIDNilai());
                mahasiswa.setNilai1(nilai1New);
            }
            mahasiswa = em.merge(mahasiswa);
            if (nilai1New != null && !nilai1New.equals(nilai1Old)) {
                Mahasiswa oldNomorIndukMahasiswaOfNilai1 = nilai1New.getNomorIndukMahasiswa();
                if (oldNomorIndukMahasiswaOfNilai1 != null) {
                    oldNomorIndukMahasiswaOfNilai1.setNilai1(null);
                    oldNomorIndukMahasiswaOfNilai1 = em.merge(oldNomorIndukMahasiswaOfNilai1);
                }
                nilai1New.setNomorIndukMahasiswa(mahasiswa);
                nilai1New = em.merge(nilai1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = mahasiswa.getNomorIndukMahasiswa();
                if (findMahasiswa(id) == null) {
                    throw new NonexistentEntityException("The mahasiswa with id " + id + " no longer exists.");
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
            Mahasiswa mahasiswa;
            try {
                mahasiswa = em.getReference(Mahasiswa.class, id);
                mahasiswa.getNomorIndukMahasiswa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mahasiswa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Nilai nilai1OrphanCheck = mahasiswa.getNilai1();
            if (nilai1OrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mahasiswa (" + mahasiswa + ") cannot be destroyed since the Nilai " + nilai1OrphanCheck + " in its nilai1 field has a non-nullable nomorIndukMahasiswa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(mahasiswa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mahasiswa> findMahasiswaEntities() {
        return findMahasiswaEntities(true, -1, -1);
    }

    public List<Mahasiswa> findMahasiswaEntities(int maxResults, int firstResult) {
        return findMahasiswaEntities(false, maxResults, firstResult);
    }

    private List<Mahasiswa> findMahasiswaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mahasiswa.class));
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

    public Mahasiswa findMahasiswa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mahasiswa.class, id);
        } finally {
            em.close();
        }
    }

    public int getMahasiswaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mahasiswa> rt = cq.from(Mahasiswa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
