package com.edu.pe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.edu.pe.models.Empleado;

@Repository
public class EmpleadoDaoImpl implements IEmpleadoDao{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void Guardar(Empleado e) {
		em.persist(e);
	}
	
	@Override
	@Transactional
	public void Actualizar(Empleado e) {
		em.merge(e);
	}

	@Override
	@Transactional
	public void Eliminar(int id) {
		Empleado obj = em.find(Empleado.class, id);   
		if(obj != null) { 
			em.remove(obj);
		}
	}

	@Override
	public List<Empleado> Listado() {
		return em.createQuery("from Empleado").getResultList();
	}
	
	@Override
	public List<Empleado> findAll() {
        String jpql = "SELECT e FROM Empleado e";
        TypedQuery<Empleado> query = em.createQuery(jpql, Empleado.class);
         
        return query.getResultList();
    }

	@Override
	public Empleado BuscarPorId(int id) {
		return em.find(Empleado.class, id);
	}

	@Override
	public List<Empleado> ListProc(int sueldo) {

	    StoredProcedureQuery query= this.em.createStoredProcedureQuery("SP_ListEmpleados");
	    query.registerStoredProcedureParameter("xsueldo", Integer.class, ParameterMode.IN);
	    
	    query.setParameter("xsueldo",sueldo);
	    query.execute();
	    List<Empleado>  list=( List<Empleado> ) query.getResultList();
	    return list;
	}
}
