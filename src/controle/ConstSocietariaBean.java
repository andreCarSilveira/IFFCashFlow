package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Empresa;
import modelo.Estoque;
import modelo.Socio;
import service.EmpresaService;
import service.SocioService;

@ViewScoped
@ManagedBean
public class ConstSocietariaBean {
	
	private Empresa empresa = new Empresa();
	private Socio socio = new Socio();
	private List<Socio> socios = new ArrayList<Socio>();

	private Double somaCapitais = 0.0;
	private Double somaPorcentagem = 0.0;
	
	@EJB
	private EmpresaService empresaService;
	
	@EJB
	private SocioService socioService;
	
	public ConstSocietariaBean() {
		empresa = new Empresa();
		socio = new Socio();
	}
	
	@PostConstruct
	public void inicializar() {
		if (empresaService.buscarEmpresa() != null) {
			empresa = empresaService.buscarEmpresa();
		}
		socios = socioService.listAll();
	}
	
	public void gravarEmpresa() {
		if (empresaService.buscarEmpresa() != null) {
			empresaService.merge(empresa);
		} else {
			empresaService.create(empresa);
		}
		empresa = empresaService.buscarEmpresa();
	}
	
	public Double calcularSomaCapital() {
        List<Socio> socios = getSocios(); // Substitua pelo método que obtém a lista de estoques do seu modelo

        double somaCapital = 0.0;

        for (Socio socio : socios) {
            if (socio.getCapital() != null) {
            	somaCapital += socio.getCapital();
            }
        }

        return somaCapital;
    }

	
	
	public void gravarSocio() {
		socioService.create(socio);
		socio = new Socio();
		socios = socioService.listAll();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public List<Socio> getSocios() {
		return socios;
	}

	public void setSocios(List<Socio> socios) {
		this.socios = socios;
	}

	public Double getSomaCapitais() {
		return somaCapitais;
	}

	public void setSomaCapitais(Double somaCapitais) {
		this.somaCapitais = somaCapitais;
	}

	public Double getSomaPorcentagem() {
		return somaPorcentagem;
	}

	public void setSomaPorcentagem(Double somaPorcentagem) {
		this.somaPorcentagem = somaPorcentagem;
	}
	
}
