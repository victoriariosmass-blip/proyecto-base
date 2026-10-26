package com.verdegreen.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entrega")
    private TipoEntrega tipoEntrega;

    @Column(name = "subtotal_productos")
    private BigDecimal subtotalProductos;

    @Column(name = "costo_envio")
    private BigDecimal costoEnvio;

    @Column(name = "costo_personalizacion")
    private BigDecimal costoPersonalizacion;

    private BigDecimal total;
    private String comprobante;
    private boolean personalizado;

    // Pedido -> Cliente (* a 1)
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Composición Pedido ◆-> DetallePedido (1 a 1..*)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id", nullable = false)
    private List<DetallePedido> detalles = new ArrayList<>();

    // Composición Pedido ◆-> CambioEstado (1 a 1..*)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id", nullable = false)
    @OrderBy("fechaHora ASC")
    private List<CambioEstado> historial = new ArrayList<>();

    protected Pedido() {
    }

    public Pedido(Cliente cliente, String comprobante, boolean personalizado,
                  TipoEntrega tipoEntrega, LocalDateTime vencimiento) {
        this.cliente = cliente;
        this.comprobante = comprobante;
        this.personalizado = personalizado;
        this.tipoEntrega = tipoEntrega;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaVencimiento = vencimiento;
        this.subtotalProductos = BigDecimal.ZERO;
        this.costoEnvio = BigDecimal.ZERO;
        this.costoPersonalizacion = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    /** Copia el producto al detalle (snapshot) y suma el subtotal al total. */
    public void agregarDetalle(Producto producto, int cantidad) {
        DetallePedido detalle = new DetallePedido(producto, cantidad);
        detalles.add(detalle);
        subtotalProductos = subtotalProductos.add(detalle.getSubtotal());
        total = total.add(detalle.getSubtotal());
    }

    /** Paso 4 del CU-01: suma al total el costo de envío y el de personalización (0 si no corresponden). */
    public void aplicarCostos(BigDecimal costoEnvio, BigDecimal costoPersonalizacion) {
        this.costoEnvio = costoEnvio;
        this.costoPersonalizacion = costoPersonalizacion;
        this.total = subtotalProductos.add(costoEnvio).add(costoPersonalizacion);
    }

    /** Cambia el estado y deja registrado el cambio con fecha y hora. */
    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        historial.add(new CambioEstado(nuevoEstado));
    }

    public boolean esADomicilio() {
        return tipoEntrega == TipoEntrega.DOMICILIO;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public BigDecimal getSubtotalProductos() {
        return subtotalProductos;
    }

    public BigDecimal getCostoEnvio() {
        return costoEnvio;
    }

    public BigDecimal getCostoPersonalizacion() {
        return costoPersonalizacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getComprobante() {
        return comprobante;
    }

    public boolean isPersonalizado() {
        return personalizado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public List<CambioEstado> getHistorial() {
        return historial;
    }
}
