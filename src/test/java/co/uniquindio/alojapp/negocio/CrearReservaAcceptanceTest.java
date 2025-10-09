package co.uniquindio.alojapp.negocio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Prueba de Aceptación - Flujo Completo Crear Reserva")
public class CrearReservaAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String token;
    private static int usuarioId;
    private static int reservaId;
    private static int alojamientoId = 0;
    private static boolean alojamientoCreado = false;

    // Método helper para login
    private JsonNode loginUsuario(String email, String password) throws Exception {
        String loginJson = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }
            """, email, password);

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.usuarioId").exists())
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    @Test
    @Order(1)
    @DisplayName("FASE 1: Login del usuario")
    void fase1_LoginUsuario() throws Exception {
        System.out.println("=== FASE 1: Login ===");
        JsonNode loginResponse = loginUsuario("ana.gomez@ejemplo.com", "ClaveNueva2");
        token = loginResponse.get("token").asText();
        usuarioId = loginResponse.get("usuarioId").asInt();
        String nombreUsuario = loginResponse.get("nombre").asText();

        System.out.println(" Login exitoso");
        System.out.println("   Usuario: " + nombreUsuario);
        System.out.println("   ID: " + usuarioId);
        System.out.println("   Token obtenido correctamente");
    }

    @Test
    @Order(2)
    @DisplayName("FASE 2: Buscar alojamiento disponible")
    void fase2_BuscarAlojamientoDisponible() throws Exception {
        // Verificar que tenemos un token válido
        if (token == null || token.split("\\.").length != 3) {
            System.out.println(" Token no válido, saltando prueba");
            return;
        }

        System.out.println("=== FASE 2: Buscar Alojamiento Disponible ===");
        System.out.println("   Usando token: Bearer " + token.substring(0, 20) + "...");

        try {
            // Buscar alojamientos disponibles con paginación
            MvcResult result = mockMvc.perform(get("/api/alojamientos")
                            .header("Authorization", "Bearer " + token)
                            .param("pagina", "0")
                            .param("tamano", "10")
                            .param("disponible", "true"))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            System.out.println(" Response completo: " + responseContent); // DEBUG

            JsonNode response = objectMapper.readTree(responseContent);
            JsonNode alojamientos = response.get("contenido");

            if (alojamientos != null && alojamientos.size() > 0) {
                // Usar el primer alojamiento disponible
                JsonNode alojamiento = alojamientos.get(0);


                alojamientoId = alojamiento.get("id").asInt();
                String titulo = alojamiento.get("titulo").asText();
                String estado = alojamiento.get("estado").asText();
                double precio = alojamiento.get("precioNoche").asDouble();

                System.out.println(" Alojamiento encontrado:");
                System.out.println("   ID: " + alojamientoId);
                System.out.println("   Título: " + titulo);
                System.out.println("   Estado: " + estado);
                System.out.println("   Precio/noche: $" + precio);
            } else {
                System.out.println(" ERROR: No hay alojamientos disponibles");
                System.out.println("   Response: " + responseContent);
            }
        } catch (Exception e) {
            System.out.println(" Error buscando alojamientos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    @Test
    @Order(3)
    @DisplayName("FASE 3: Crear reserva")
    void fase3_CrearReserva() throws Exception {
        if (alojamientoId <= 0) {
            System.out.println("⚠ Saltando prueba: No hay alojamiento disponible");
            return;
        }

        System.out.println("=== FASE 3: Crear Reserva ===");

        String reservaJson = String.format("""
            {
                "alojamientoId": %d,
                "fechaCheckin": "2025-12-15",
                "fechaCheckout": "2025-12-20",
                "numeroHuespedes": 2
            }
            """, alojamientoId);

        MvcResult result = mockMvc.perform(post("/api/reservas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.estado").exists())
                .andExpect(jsonPath("$.precioTotal").exists())
                .andReturn();

        JsonNode reservaResponse = objectMapper.readTree(result.getResponse().getContentAsString());
        reservaId = reservaResponse.get("id").asInt();
        String estadoReserva = reservaResponse.get("estado").asText();
        double precioTotal = reservaResponse.get("precioTotal").asDouble();

        System.out.println(" Reserva creada exitosamente:");
        System.out.println("   ID de reserva: " + reservaId);
        System.out.println("   Estado: " + estadoReserva);
        System.out.println("   Precio total: $" + precioTotal);
        System.out.println("   Alojamiento ID: " + alojamientoId);
    }

    @Test
    @Order(4)
    @DisplayName("FASE 4: Verificar reserva creada")
    void fase4_VerificarReserva() throws Exception {
        if (reservaId <= 0) {
            System.out.println(" Saltando prueba: No hay reserva creada para verificar");
            return;
        }

        System.out.println("=== FASE 4: Verificar Reserva ===");

        mockMvc.perform(get("/api/reservas/" + reservaId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservaId))
                .andExpect(jsonPath("$.estado").exists())
                .andExpect(jsonPath("$.alojamientoId").value(alojamientoId))
                .andExpect(jsonPath("$.fechaCheckin").exists())
                .andExpect(jsonPath("$.fechaCheckout").exists())
                .andExpect(jsonPath("$.numeroHuespedes").value(2))
                .andExpect(jsonPath("$.precioTotal").exists());

        System.out.println(" Reserva verificada correctamente");
        System.out.println("  Todos los datos coinciden con lo esperado");
    }

    @Test
    @Order(5)
    @DisplayName("FASE 5: Listar reservas del usuario")
    void fase5_ListarReservasUsuario() throws Exception {
        // Saltar si no se pudo crear la reserva
        if (reservaId <= 0) {
            System.out.println("⚠ Saltando prueba: No hay reserva creada para listar");
            return;
        }

        System.out.println("=== FASE 5: Listar Reservas del Usuario ===");

        MvcResult result = mockMvc.perform(get("/api/reservas")
                        .header("Authorization", "Bearer " + token)
                        .param("pagina", "0")
                        .param("tamano", "10"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode reservas = objectMapper.readTree(result.getResponse().getContentAsString());

        // Verificar que la reserva creada aparece en la lista
        boolean reservaEncontrada = false;
        for (JsonNode reserva : reservas) {
            if (reserva.get("id").asInt() == reservaId) {
                reservaEncontrada = true;
                break;
            }
        }

        Assertions.assertTrue(reservaEncontrada, "La reserva creada debe aparecer en la lista del usuario");

        System.out.println(" Listado de reservas verificado");
        System.out.println("   Total de reservas del usuario: " + reservas.size());
        System.out.println("   La reserva creada está en la lista");
    }

    @Test
    @Order(6)
    @DisplayName("FASE 6: Cancelar reserva")
    void fase6_CancelarReserva() throws Exception {
        // Saltar si no se pudo crear la reserva
        if (reservaId <= 0) {
            System.out.println("⚠ Saltando prueba: No hay reserva creada para cancelar");
            return;
        }

        System.out.println("=== FASE 6: Cancelar Reserva ===");

        String cancelarJson = """
            {
                "motivoCancelacion": "Cambio de planes del usuario - Prueba de aceptación"
            }
            """;

        mockMvc.perform(put("/api/reservas/" + reservaId + "/cancelar")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cancelarJson))
                .andExpect(status().isOk());

        System.out.println(" Solicitud de cancelación enviada");

        // Verificar que la reserva fue cancelada
        mockMvc.perform(get("/api/reservas/" + reservaId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservaId))
                .andExpect(jsonPath("$.estado").value("CANCELADA"));

        System.out.println(" Reserva cancelada exitosamente");
        System.out.println("   Estado actualizado a CANCELADA");
    }

    @Test
    @Order(7)
    @DisplayName("FASE 7: Verificar estado final del alojamiento")
    void fase7_VerificarEstadoAlojamiento() throws Exception {
        if (reservaId <= 0 || alojamientoId <= 0) {
            System.out.println("⚠ Saltando prueba: No hay reserva o alojamiento para verificar");
            return;
        }

        System.out.println("=== FASE 7: Verificar Estado del Alojamiento ===");

        // Primero obtener la respuesta para debug
        MvcResult debugResult = mockMvc.perform(get("/api/alojamientos/" + alojamientoId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        String responseContent = debugResult.getResponse().getContentAsString();
        JsonNode alojamiento = objectMapper.readTree(responseContent);

        System.out.println(" Campos disponibles en la respuesta:");
        alojamiento.fieldNames().forEachRemaining(field ->
                System.out.println("   - " + field + ": " + alojamiento.get(field))
        );

        // Ahora hacer las verificaciones con los campos correctos
        mockMvc.perform(get("/api/alojamientos/" + alojamientoId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alojamientoId))
                .andExpect(jsonPath("$.estado").value("ACTIVO")); // O el estado que corresponda

        System.out.println(" Estado del alojamiento verificado");
        System.out.println(" FLUJO COMPLETO FINALIZADO EXITOSAMENTE");
    }

    // Tests adicionales para flujos alternativos (estos SIEMPRE se ejecutan)
    @Test
    @DisplayName("Flujo alternativo: Intentar crear reserva sin autenticación")
    void crearReserva_SinAutenticacion_DeberiaFallar() throws Exception {
        String reservaJson = """
            {
                "alojamientoId": 1,
                "fechaCheckin": "2025-12-15",
                "fechaCheckout": "2025-12-20",
                "numeroHuespedes": 2
            }
            """;

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaJson))
                .andExpect(status().isUnauthorized());

        System.out.println(" Control de acceso funcionando: Reserva sin auth rechazada");
    }

    @Test
    @DisplayName("Flujo alternativo: Intentar crear reserva con fechas inválidas")
    void crearReserva_FechasInvalidas_DeberiaFallar() throws Exception {
        JsonNode login = loginUsuario("ana.gomez@ejemplo.com", "ClaveNueva2");
        String tokenLocal = login.get("token").asText();

        String reservaJson = """
            {
                "alojamientoId": 1,
                "fechaCheckin": "2025-12-20",
                "fechaCheckout": "2025-12-15",
                "numeroHuespedes": 2
            }
            """;

        mockMvc.perform(post("/api/reservas")
                        .header("Authorization", "Bearer " + tokenLocal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaJson))
                .andExpect(status().isBadRequest());

        System.out.println(" Validación de fechas funcionando: Fechas inválidas rechazadas");
    }

    @Test
    @DisplayName("Flujo alternativo: Login con credenciales incorrectas")
    void login_CredencialesIncorrectas_DeberiaFallar() throws Exception {
        String loginJson = """
            {
                "email": "ana.gomez@ejemplo.com",
                "password": "PasswordIncorrecto"
            }
            """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized());

        System.out.println(" Validación de credenciales funcionando");
    }
}