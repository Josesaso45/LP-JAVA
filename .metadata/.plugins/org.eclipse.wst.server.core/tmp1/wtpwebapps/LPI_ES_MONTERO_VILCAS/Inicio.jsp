<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Transporte Urbano</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            background-color: #f8f9fa;
        }
        .login-form {
            width: 100%;
            max-width: 400px;
            padding: 2rem;
            border: 1px solid #dee2e6;
            border-radius: 0.5rem;
            background-color: #fff;
        }
    </style>
</head>
<body>
    <div class="login-form">
        <h2 class="text-center mb-4">Urbanito Transporte Rápido</h2>
        <h4 class="text-center mb-4">Inicio de Sesión</h4>
        
        <form id="loginForm" action="login" method="post">
            <div class="mb-3">
                <label for="usuario" class="form-label">Usuario</label>
                <input type="text" class="form-control" id="usuario" name="usuario" value="admin" required>
            </div>
            <div class="mb-3">
                <label for="clave" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="clave" name="clave" value="123" required>
            </div>
            
            <div id="errorMessage" class="alert alert-danger" role="alert" style="display:none;"></div>
            
            <button type="submit" class="btn btn-primary w-100">Ingresar</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script>
        $(document).ready(function() {
            // Se intercepta el evento de envío del formulario
            $('#loginForm').on('submit', function(event) {
                // 1. Evita que el formulario se envíe de la forma tradicional (recargando la página)
                event.preventDefault(); 

                var $form = $(this);
                var $submitButton = $form.find('button[type="submit"]');
                var $errorMessage = $('#errorMessage');

                // Ocultar errores previos y deshabilitar el botón
                $errorMessage.hide();
                $submitButton.prop('disabled', true).text('Ingresando...');

                // 2. Se envían los datos al servlet usando AJAX
                $.ajax({
                    type: 'POST',
                    url: $form.attr('action'), // La URL es 'login'
                    data: $form.serialize(),   // Recopila los datos del formulario (usuario y clave)
                    
                    // 3. Si el servlet responde con éxito (no hay error)
                    success: function(response) {
                        // El servlet redirigirá, y el navegador seguirá esa redirección automáticamente
                        // a la página de ListadoTransporte.jsp
                        window.location.href = "asignacion?accion=listar";
                    },
                    
                    // 4. Si el servlet responde con un error (ej. credenciales incorrectas)
                    error: function(jqXHR) {
                        var errorMsg = "Error de conexión. Intente de nuevo.";
                        if (jqXHR.responseText) {
                            errorMsg = jqXHR.responseText; // Muestra el mensaje de error del servlet
                        }
                        $errorMessage.text(errorMsg).show();
                        $submitButton.prop('disabled', false).text('Ingresar'); // Habilita el botón de nuevo
                    }
                });
            });
        });
    </script>
</body>
</html>