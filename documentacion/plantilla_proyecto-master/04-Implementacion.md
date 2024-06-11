# Implementación

Explicación de como he hecho la lógica para mandar un gmail:

## MailConfig.java
Necesitamos un config para indicar a spring la configuración que vamos a usar,en este caso lo hacemos con la anotación,@Bean indicandole que puede encontrar el valor de estas variables en nuestro archivo properties con el nombre correspondiente,además usamos los métodos de la clase javaMail que previamente estan importados para poder usarlos,donde indicamos puerto,correo,protocolo(smtp),credencial del corrreo que vamos a usar.
```java
@Configuration
public class MailConfig {
    
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String mail;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(mail);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
```

## MailController.java
Necesitamos una controlador para indicar el endpoint al que vamos a dirirgir al usuario para así poder hacer la petición al servidor de gmail y este nos de el okey para poder mandar el correo a la persona correspondiente
```java
@RestController
@RequestMapping("/help")
public class MailController {
    
    @Autowired
    private MailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody MailDTO mailDTO){

        System.out.println("Mensaje recibido: "+ mailDTO);

        emailService.sendMail(mailDTO.getTo(), mailDTO.getSubject(), mailDTO.getMessage());
        Map<String,String> response = new HashMap<>();
        response.put("estado","Enviado");

        return (ResponseEntity<?>) ResponseEntity.ok(response);
    }
    

}
```

## MailDTO.java
Al nosotros mandar el correo,lo mandamos en formato json y necesitamos de alguna manera pasar nuestros datos del formulario a json,para ello necesitamos esta clase con la que pasaremos nuestros datos a json
```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
    private String[] to;
    private String subject;
    private String message;
}
```


## MailRepo.java
Interfaz que controla el envio de correo
```java
public interface MailRepo {
    void sendMail(String[] to,String subject,String message);
}
```

## MailService.java
Clase que implementa la interfaz de MailRepo y que es la que se encarga de enviar el correo
```java
@Service
public class MailService implements MailRepo{
    
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String[] to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(from);
        mailMessage.setTo("Correo que recibe el mensaje");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

}
```



## Mandar email desde el html
Necesitamos hacer un formulario y desde el script recoger los datos con el dom y una vez recogidos hacer una petición para poder mandar el correo
```javascript
<script src="https://smtpjs.com/v3/smtp.js"></script>

    <script>
        const enviarCorreo = (event) => {
            event.preventDefault(); // Evita que se envíe el formulario directamente
            
            const name = document.getElementById('name').value;
            const phone = document.getElementById('phone').value;
            const asunto = document.getElementById('subject').value;
            const mail = document.getElementById('mail').value;
            const message = document.getElementById('message').value;

            const mailTo = {
                to: ["Correo que recibe el mensaje"],
                subject: asunto,
                message: `Nombre: ${name}\nTeléfono: ${phone}\nEmail: ${mail}\nMensaje: ${message}`
            };

            fetch('/help/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(mailTo),
            })
            .then(response => {
                if (response.ok) {
                    alert('Correo enviado correctamente');
                } else {
                    alert('Error al enviar el correo');
                }
            })
            .catch(error => {
                console.error('Error al enviar la solicitud:', error);
                alert('Error al enviar el correo');
            });
        };
    </script>
```

## application.properties
```properties
spring.mail.host=dominio
spring.mail.port=puerto_que_usa_gmail
spring.mail.username=persona_enviar_correo
spring.mail.password=password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
-------
Para hacer la lógica de mandar el correo una vez el pedido ha terminado,uso el siguiente método:
```java
    public void sendMailToLoggedInUser(String subject, String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            /*
             * Es un método que tiene la clase Authentication de donde saco el Objeto el cuál está actualmente logueado
             */
            Object principal = authentication.getPrincipal();
            String email = null;

            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                email = principal.toString();
            }

            if (email != null) {
                sendMail(new String[]{email}, subject, message);
            }
        }
    }
```
Con este código lo que obtengo es que el usuario el cúal está haciendo el pedido,es decir está logueado,recojo su email,compruebo si es nulo o no y con la ayuda de un instaceof de UserDetails,puedo obtener el nombre del usuario y con ello puedo obtener el email del usuario y paso el email a string y lo mando todo lo importante del pedido.

\pagebreak
