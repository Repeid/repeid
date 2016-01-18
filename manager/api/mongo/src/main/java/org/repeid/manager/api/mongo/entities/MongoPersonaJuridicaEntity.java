package org.repeid.manager.api.mongo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

@Entity
@Table(name = "PERSONA_JURIDICA")
@NamedQueries(value = {
		@NamedQuery(name = "MongoPersonaJuridicaEntity.findAll", query = "SELECT p FROM PersonaJuridicaEntity p"),
		@NamedQuery(name = "MongoPersonaJuridicaEntity.findByTipoDocumento", query = "SELECT p FROM PersonaJuridicaEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento"),
		@NamedQuery(name = "MongoPersonaJuridicaEntity.findByTipoNumeroDocumento", query = "SELECT p FROM PersonaJuridicaEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento AND p.numeroDocumento = :numeroDocumento"),
		@NamedQuery(name = "MongoPersonaJuridicaEntity.findByIdPersonaNaturalRepresentanteLegal", query = "SELECT p FROM PersonaJuridicaEntity p INNER JOIN p.representanteLegal rl WHERE rl.id = :idPersonaNaturalRepresentanteLegal"),
		@NamedQuery(name = "MongoPersonaJuridicaEntity.findByFilterText", query = "SELECT p FROM PersonaJuridicaEntity p WHERE LOWER(p.numeroDocumento) LIKE :filterText OR LOWER(p.razonSocial) LIKE :filterText OR LOWER(p.nombreComercial) LIKE :filterText") })
public class MongoPersonaJuridicaEntity extends MongoPersonaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@NotNull
	@Size(min = 1, max = 70)
	@NotBlank
	@Column(name = "razon_social")
	private String razonSocial;

	@Size(min = 0, max = 50)
	@Column(name = "nombre_comercial")
	private String nombreComercial;

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_constitucion")
	private Date fechaConstitucion;

	@Size(min = 0, max = 70)
	@Column(name = "actividad_principal")
	private String actividadPrincipal;

	@NotNull
	@Size(min = 0, max = 50)
	@Column(name = "tipo_empresa")
	private String tipoEmpresa;

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	@Column(name = "fin_lucro")
	private boolean finLucro;

	@NotNull
	@OneToOne
	@JoinColumn(name = "representante_legal_id", foreignKey = @ForeignKey )
	private MongoPersonaNaturalEntity representanteLegal;

	@OneToMany(mappedBy = "personaJuridica", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {
			CascadeType.REMOVE })
	private Set<MongoAccionistaEntity> accionistas = new HashSet<MongoAccionistaEntity>(0);

	public MongoPersonaJuridicaEntity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}

	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	public String getActividadPrincipal() {
		return actividadPrincipal;
	}

	public void setActividadPrincipal(String actividadPrincipal) {
		this.actividadPrincipal = actividadPrincipal;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public boolean isFinLucro() {
		return finLucro;
	}

	public void setFinLucro(boolean finLucro) {
		this.finLucro = finLucro;
	}

	public MongoPersonaNaturalEntity getRepresentanteLegal() {
		return representanteLegal;
	}

	public void setRepresentanteLegal(MongoPersonaNaturalEntity representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public Set<MongoAccionistaEntity> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Set<MongoAccionistaEntity> accionistas) {
		this.accionistas = accionistas;
	}

	@Override
	public String toString() {
		return "(PersonaJuridicaEntity id=" + this.id + " tipoDocumento=" + this.tipoDocumento.getAbreviatura()
				+ " numeroDocumento" + this.numeroDocumento + " razonSocial=" + this.razonSocial + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MongoPersonaJuridicaEntity))
			return false;
		MongoPersonaJuridicaEntity other = (MongoPersonaJuridicaEntity) obj;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

}
