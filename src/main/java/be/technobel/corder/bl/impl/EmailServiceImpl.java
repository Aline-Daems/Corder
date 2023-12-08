package be.technobel.corder.bl.impl;

import be.technobel.corder.dal.models.Participation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    private final JavaMailSender mailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(Participation participation) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("info@corder.com");
            helper.setTo(participation.getParticipantEmail());
            helper.setSubject("Jeu concours Corder");

            String htmlContent = """
                    <body>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:20pt">
                    \t\t\t\t<span class="Strong" style="font-family:'Calibri Light'; color:#37797b">Merci !</span><span class="Strong" style="font-family:'Calibri Light'; color:#37797b">&#xa0;</span><span class="Strong" style="font-family:'Calibri Light'; color:#37797b">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">Merci d’avoir rapporté vos déchets de pesticides au recyparc ! Dès que notre équipe aura validé votre photo, un kit de semences locales et biologiques Cycle-en-terre vous sera envoyé par la poste.</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">Et pour vous remercier de votre geste pour l’environnement, le 21 juin, un tirage au sort désignera un·e gagnant·e d’un bon cadeau de 200 € à dépenser en jardinerie labellisée "Jardiner Sans Pesticides". Le bon sera envoyé au·à la gagnant·e par mail. </span><span class="Strong" style="font-family:'Calibri Light'; color:#37797b">Bonne chance</span><span class="Strong" style="font-family:'Calibri Light'; color:#37797b">&#xa0;</span><span class="Strong" style="font-family:'Calibri Light'; color:#37797b">!</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify">
                    \t\t\t\t<span style="font-family:'Calibri Light'; font-size:11pt">Pour découvrir les jardineries labellisées "Jardiner Sans Pesticides" sans plus attendre, rendez-vous sur</span><span style="font-family:'Calibri Light'; font-size:11pt">&#xa0;</span><a href="http://www.jardinersanspesticides.be" style="text-decoration:none"><span class="Strong" style="font-family:'Calibri Light'; font-size:11pt; text-decoration:underline; color:#37797b">www.jardinersanspesticides.be</span></a><span class="Strong" style="font-family:'Calibri Light'; font-size:11pt; text-decoration:underline; color:#37797b">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify">
                    \t\t\t\t<span style="font-family:'Calibri Light'; font-size:11pt">Si vous souhaitez continuer à en apprendre plus sur le jardinage durable ou si vous vous posez des questions à ce propos, rendez-vous sur notre site web</span><span style="font-family:'Calibri Light'; font-size:11pt">&#xa0;</span><a href="https://corder.be/fr/comite-regional-phyto/utilisateurs-non-professionnels" target="_blank" title="https://corder.be/fr/comite-regional-phyto/utilisateurs-non-professionnels" style="text-decoration:none"><span class="Strong" style="font-family:'Calibri Light'; font-size:11pt; text-decoration:underline; color:#37797b">www.corder.be</span></a><span style="font-family:'Calibri Light'; font-size:11pt">&#xa0;</span><span style="font-family:'Calibri Light'; font-size:11pt">ou par mail à</span><span style="font-family:'Calibri Light'; font-size:11pt">&#xa0;</span><a href="info@corder.be" target="_blank" title="mailto:info@corder.be" style="text-decoration:none"><span class="Strong" style="font-family:'Calibri Light'; font-size:11pt; text-decoration:underline; color:#37797b"><span class="__cf_email__" data-cfemail="335a5d555c73505c415756411d5156">info@corder.be</span></span></a><span style="font-family:'Calibri Light'; font-size:11pt">&#xa0;</span><span style="font-family:'Segoe UI Emoji'; font-size:11pt">\uD83C\uDF31</span><span style="font-family:'Calibri Light'; font-size:11pt">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:justify; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:right; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">A bientôt !</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:0pt; margin-bottom:0pt; text-align:right; font-size:11pt">
                    \t\t\t\t<span style="font-family:'Calibri Light'">L'asbl CORDER</span><span style="font-family:'Calibri Light'">&#xa0;</span>
                    \t\t\t</p>
                    \t\t\t<p class="NormalWeb" style="margin-top:14pt; margin-bottom:6pt; text-align:right; font-size:11pt">
                    \t\t\t\t<em><span style="font-family:'Calibri Light'; ">L’asbl CORDER s’intègre dans une série de démarches visant à favoriser la protection durable des végétaux en Wallonie</span></em>
                    \t\t\t</p>
                    \t\t\t<p>
                    \t\t\t\t&#xa0;
                    \t\t\t</p>
                    </body>""";
            helper.setText(htmlContent, true); // `true` indicates that this is HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("An error occurred while sending mail: ", e);
        }
    }
}
