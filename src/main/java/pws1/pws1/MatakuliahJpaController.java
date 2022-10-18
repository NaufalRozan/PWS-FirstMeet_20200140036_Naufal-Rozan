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
public class MatakuliahJpaController implements Serializable {

    public MatakuliahJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pws1_pws1_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public MatakuliahJpaController() {
    }
    
    

    public void create(Matakuliah matakuliah) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Nilai nilaiOrphanCheck = matakuliah.getNilai();
        if (nilaiOrphanCheck != null) {
            Matakuliah oldMatakuliahOfNilai = nilaiOrphanCheck.getMatakuliah();
            if (oldMatakuliahOfNilai != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Nilai " + nilaiOrphanCheck + " already has an item of type Matakuliah whose nilai column cannot be null. Please make another selection for the nilai field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nilai nilai = matakuliah.getNilai();
            if (nilai != null) {
                nilai = em.getReference(nilai.getClass(), nilai.getIDNilai());
                matakuliah.setNilai(nilai);
            }
            em.persist(matakuliah);
            if (nilai != null) {
                nilai.setMatakuliah(matakuliah);
                nilai = em.merge(nilai);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMatakuliah(matakuliah.getKodeMK()) != null) {
                throw new PreexistingEntityException("Matakuliah " + matakuliah + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matakuliah matakuliah) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matakuliah persistentMatakuliah = em.find(Matakuliah.class, matakuliah.getKodeMK());
            Nilai nilaiOld = persistentMatakuliah.getNilai();
            Nilai nilaiNew = matakuliah.getNilai();
            List<String> illegalOrphanMessages = null;
            if (nilaiNew != null && !nilaiNew.equals(nilaiOld)) {
                Matakuliah oldMatakuliahOfNilai = nilaiNew.getMatakuliah();
                if (oldMatakuliahOfNilai != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Nilai " + nilaiNew + " already has an item of type Matakuliah whose nilai column cannot be null. Please make another selection for the nilai field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (nilaiNew != null) {
                nilaiNew = em.getReference(nilaiNew.getClass(), nilaiNew.getIDNilai());
                matakuliah.setNilai(nilaiNew);
            }
            matakuliah = em.merge(matakuliah);
            if (nilaiOld != null && !nilaiOld.equals(nilaiNew)) {
                nilaiOld.setMatakuliah(null);
                nilaiOld = em.merge(nilaiOld);
            }
            if (nilaiNew != null && !nilaiNew.equals(nilaiOld)) {
                nilaiNew.setMatakuliah(matakuliah);
                nilaiNew = em.merge(nilaiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = matakuliah.getKodeMK();
                if (findMatakuliah(id) == null) {
                    throw new NonexistentEntityException("The matakuliah with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matakuliah matakuliah;
            try {
                matakuliah = em.getReference(Matakuliah.class, id);
                matakuliah.getKodeMK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matakuliah with id " + id + " no longer exists.", enfe);
            }
            Nilai nilai = matakuliah.getNilai();
            if (nilai != null) {
                nilai.setMatakuliah(null);
                nilai = em.merge(nilai);
            }
            em.remove(matakuliah);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matakuliah> findMatakuliahEntities() {
        return findMatakuliahEntities(true, -1, -1);
    }

    public List<Matakuliah> findMatakuliahEntities(int maxResults, int firstResult) {
        return findMatakuliahEntities(false, maxResults, firstResult);
    }

    private List<Matakuliah> findMatakuliahEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matakuliah.class));
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

    public Matakuliah findMatakuliah(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matakuliah.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatakuliahCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matakuliah> rt = cq.from(Matakuliah.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
