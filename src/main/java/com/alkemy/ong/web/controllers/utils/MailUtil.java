package com.alkemy.ong.web.controllers.utils;

import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    public String mailTemplate(String title, String body) {
        return "<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "      xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <style type=\"text/css\">\n" +
                "    #outlook a {\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    .ReadMsgBody {\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .ExternalClass {\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    .ExternalClass * {\n" +
                "      line-height: 100%;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -webkit-text-size-adjust: 100%;\n" +
                "      -ms-text-size-adjust: 100%;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    td {\n" +
                "      border-collapse: collapse;\n" +
                "      mso-table-lspace: 0pt;\n" +
                "      mso-table-rspace: 0pt;\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "      border: 0;\n" +
                "      height: auto;\n" +
                "      line-height: 100%;\n" +
                "      outline: none;\n" +
                "      text-decoration: none;\n" +
                "      -ms-interpolation-mode: bicubic;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      display: block;\n" +
                "      margin: 13px 0;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <style type=\"text/css\">\n" +
                "    @import url(https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700);\n" +
                "    @import url(https://fonts.googleapis.com/css?family=Cabin:400,700);\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </style>\n" +
                "    <!--<![endif]-->\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "    @media only screen and (min-width:480px) {\n" +
                "      .mj-column-per-100 {\n" +
                "        width: 100% !important;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "    @media only screen and (max-width:480px) {\n" +
                "      table.full-width-mobile {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      td.full-width-mobile {\n" +
                "        width: auto !important;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </style>\n" +
                "    <style type=\"text/css\">\n" +
                "    .hide_on_mobile {\n" +
                "      display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (min-width: 480px) {\n" +
                "      .hide_on_mobile {\n" +
                "        display: block !important;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    .hide_section_on_mobile {\n" +
                "      display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (min-width: 480px) {\n" +
                "      .hide_section_on_mobile {\n" +
                "        display: table !important;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    .hide_on_desktop {\n" +
                "      display: block !important;\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (min-width: 480px) {\n" +
                "      .hide_on_desktop {\n" +
                "        display: none !important;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    .hide_section_on_desktop {\n" +
                "      display: table !important;\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (min-width: 480px) {\n" +
                "      .hide_section_on_desktop {\n" +
                "        display: none !important;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    [owa] .mj-column-per-100 {\n" +
                "      width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    [owa] .mj-column-per-50 {\n" +
                "      width: 50% !important;\n" +
                "    }\n" +
                "\n" +
                "    [owa] .mj-column-per-33 {\n" +
                "      width: 33.333333333333336% !important;\n" +
                "    }\n" +
                "\n" +
                "    p,\n" +
                "    h1,\n" +
                "    h2,\n" +
                "    h3 {\n" +
                "      margin: 0px;\n" +
                "    }\n" +
                "\n" +
                "    a {\n" +
                "      text-decoration: none;\n" +
                "      color: inherit;\n" +
                "    }\n" +
                "\n" +
                "    @media only print and (min-width:480px) {\n" +
                "      .mj-column-per-100 {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .mj-column-per-40 {\n" +
                "        width: 40% !important;\n" +
                "      }\n" +
                "\n" +
                "      .mj-column-per-60 {\n" +
                "        width: 60% !important;\n" +
                "      }\n" +
                "\n" +
                "      .mj-column-per-50 {\n" +
                "        width: 50% !important;\n" +
                "      }\n" +
                "\n" +
                "      mj-column-per-33 {\n" +
                "        width: 33.333333333333336% !important;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color:#52c0f7;\">\n" +
                "\n" +
                "<div style=\"background-color:#52c0f7;\">\n" +
                "\n" +
                "    <div style=\"Margin:0px auto;max-width:600px;\">\n" +
                "\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td style=\"direction:ltr;font-size:0px;padding:9px 0px 9px 0px;text-align:center;vertical-align:top;\">\n" +
                "\n" +
                "                    <div class=\"mj-column-per-100 outlook-group-fix\"\n" +
                "                         style=\"font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
                "                               style=\"vertical-align:top;\"\n" +
                "                               width=\"100%\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "                                    <div style=\"height:50px;\">\n" +
                "                                        &nbsp;\n" +
                "                                    </div>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "\n" +
                "                    </div>\n" +
                "\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "    <div style=\"background:#FFFFFF;background-color:#FFFFFF;Margin:0px auto;max-width:600px;\">\n" +
                "\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
                "               style=\"background:#FFFFFF;background-color:#FFFFFF;width:100%;\">\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td style=\"direction:ltr;font-size:0px;padding:9px 0px 9px 0px;text-align:center;vertical-align:top;\">\n" +
                "                    <div class=\"mj-column-per-100 outlook-group-fix\"\n" +
                "                         style=\"font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
                "                               style=\"vertical-align:top;\"\n" +
                "                               width=\"100%\">\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <div style=\"height:30px;\">\n" +
                "                                        &nbsp;\n" +
                "                                    </div>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td align=\"center\" style=\"font-size:0px;padding:0px 0px 0px 0px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
                "                                           style=\"border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                        <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:312px;\">\n" +
                "                                                <img height=\"auto\"\n" +
                "                                                     src=\"https://s3-eu-west-1.amazonaws.com/topolio/uploads/6082338c1a7ee/1619145692.jpg\"\n" +
                "                                                     style=\"border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:13px;\"\n" +
                "                                                     width=\"312\">\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <div style=\"height:50px;\">&nbsp;\n" +
                "                                    </div>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td align=\"left\"\n" +
                "                                    style=\"font-size:0px;padding:15px 15px 15px 15px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <div\n" +
                "                                            style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:1.5;text-align:left;color:#000000;\">\n" +
                "                                        <p style=\"text-align: center;\"><span\n" +
                "                                                style=\"font-size: 24px;\"><strong>" + title + " </strong></span>\n" +
                "                                        </p>\n" +
                "                                        <p style=\"text-align: center;\">&nbsp;</p>\n" +
                "                                        <p style=\"text-align: justify;\"><span\n" +
                "                                                style=\"font-size: 16px;\">\n" + body +
                "                                    </div>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <div style=\"height:30px;\">&nbsp;\n" +
                "                                    </div>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td align=\"left\"\n" +
                "                                    style=\"font-size:0px;padding:15px 15px 15px 15px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <div\n" +
                "                                            style=\"font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:1.5;text-align:left;color:#000000;\">\n" +
                "                                        <p style=\"text-align: center;\">\n" +
                "                                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                               style=\"color:rgb(0,0,0);font-family:Roboto,RobotoDraft,Helvetica,Arial,sans-serif;font-size:medium;border-collapse:collapse\">\n" +
                "                                            <tbody>\n" +
                "                                            <tr>\n" +
                "                                                <td style=\"font-family:Muli,sans-serif,Arial;border-collapse:collapse;font-size:15px;line-height:24px;font-weight:700;color:rgb(26,26,26);padding:3px 0px 0px\">\n" +
                "                                                    Equipo de Admisiones\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                            <tr>\n" +
                "                                                <td style=\"font-family:Muli,sans-serif,Arial;border-collapse:collapse;font-size:10px;line-height:14px;color:rgb(154,159,170);letter-spacing:1px;padding:1px 0px 0px\">\n" +
                "                                                    #Alkcelerate\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                            <tr>\n" +
                "                                                <td style=\"border-collapse:collapse;padding:15px 0px 0px\">\n" +
                "                                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                           style=\"border-collapse:collapse\">\n" +
                "                                                        <tbody>\n" +
                "                                                        <tr>\n" +
                "                                                            <td style=\"font-family:Muli,sans-serif,Arial;border-collapse:collapse;font-size:10px;line-height:5px;color:rgb(47,53,66);letter-spacing:0px\">\n" +
                "                                                                <span style=\"color:rgb(82,190,245);font-weight:900;font-size:12px\">T</span>&nbsp;" +
                "                                                                       <span\n" +
                "                                                                    style=\"color:rgb(7,157,243);font-size:13px\">:</span>&nbsp;<a\n" +
                "                                                                    href=\"https://wa.link/jhm3ke\" target=\"_blank\"\n" +
                "                                                                    data-saferedirecturl=\"https://www.google.com/url?q=https://wa.link/jhm3ke&amp;source=gmail&amp;ust=1651684276372000&amp;usg=AOvVaw3HhZPFsdsXN9r8z4aA-RhW\"><font\n" +
                "                                                                    color=\"#1155cc\">+54 (11</font>) 2593-6669&nbsp;</a>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                        <tr>\n" +
                "                                                            <td style=\"border-collapse:collapse;padding:0px\">\n" +
                "                                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\"\n" +
                "                                                                       cellpadding=\"0\" style=\"border-collapse:collapse\">\n" +
                "                                                                    <tbody>\n" +
                "                                                                    <tr>\n" +
                "                                                                        <td style=\"font-family:Muli,sans-serif,Arial;border-collapse:collapse;font-size:10px;line-height:24px;color:rgb(47,53,66);letter-spacing:0px\">\n" +
                "                                                                            <span style=\"color:rgb(82,190,245);font-weight:900;font-size:12px\">E</span>&nbsp;<span\n" +
                "                                                                                style=\"color:rgb(82,190,245);font-size:13px\">:</span>&nbsp;contacto<a\n" +
                "                                                                                href=\"mailto:federico@alkemy.org\"\n" +
                "                                                                                style=\"color:rgb(47,53,66)\"\n" +
                "                                                                                target=\"_blank\">@alkemy.org</a></td>\n" +
                "                                                                    </tr>\n" +
                "                                                                    </tbody>\n" +
                "                                                                </table>\n" +
                "                                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\"\n" +
                "                                                                       cellpadding=\"0\" style=\"border-collapse:collapse\">\n" +
                "                                                                    <tbody>\n" +
                "                                                                    <tr>\n" +
                "                                                                        <td style=\"border-collapse:collapse;padding:7px 0px 0px\">\n" +
                "                                                                            <img src=\"https://ci3.googleusercontent.com/proxy/v3t0RVu67b2K2sCdYxvm4DQrgm24EI_xkUX4MEMdWXiBX1CGqd39a7gTaG5ttW1jlx8CXow_lAvfLE1XkMvn-Xw=s0-d-e1-ft#https://i.postimg.cc/nV0QZ6hS/alkemy-firma.jpg\"\n" +
                "                                                                                 alt=\"alkemy-firma\" width=\"100\"\n" +
                "                                                                                 border=\"0\"\n" +
                "                                                                                 style=\"outline:none;border:0px;display:inline-block\"\n" +
                "                                                                                 class=\"CToWUd\"></td>\n" +
                "                                                                    </tr>\n" +
                "                                                                    <tr>\n" +
                "                                                                        <td style=\"border-collapse:collapse;padding:7px 0px 0px\">\n" +
                "                                                                            <a href=\"https://www.linkedin.com/company/alkemy2020/about/?viewAsMember=true\"\n" +
                "                                                                               style=\"color:rgb(17,85,204)\"\n" +
                "                                                                               target=\"_blank\"\n" +
                "                                                                               data-saferedirecturl=\"https://www.google.com/url?q=https://www.linkedin.com/company/alkemy2020/about/?viewAsMember%3Dtrue&amp;source=gmail&amp;ust=1651684276372000&amp;usg=AOvVaw1Q7IdS_LazgMkvRdZPdXMX\"><img\n" +
                "                                                                                    src=\"https://ci3.googleusercontent.com/proxy/anT_DOxGbvkQRXsHMJXU7toGkn2GBgZD17zRTKFSlTxC_wg-UVqeYVrAKLC4FS5BQZjw-kkmTXXUXYk-0C_trQXvpbA=s0-d-e1-ft#https://i.postimg.cc/kXhVRHhW/linkedin-alkemy.png\"\n" +
                "                                                                                    alt=\"LinkedIN\" width=\"14\" border=\"\"\n" +
                "                                                                                    style=\"outline:none;border:0px;display:inline-block\"\n" +
                "                                                                                    class=\"CToWUd\"></a>&nbsp;<a\n" +
                "                                                                                href=\"https://twitter.com/alkemy__\"\n" +
                "                                                                                style=\"color:rgb(17,85,204)\"\n" +
                "                                                                                target=\"_blank\"\n" +
                "                                                                                data-saferedirecturl=\"https://www.google.com/url?q=https://twitter.com/alkemy__&amp;source=gmail&amp;ust=1651684276372000&amp;usg=AOvVaw3k46l1EK4Oqtq_9o4ck9VE\"><img\n" +
                "                                                                                src=\"https://ci4.googleusercontent.com/proxy/mTBk6oYNFTaYivevmd8CWs-D1npFcr0kR2qNItiOhMPJqN82iKXwtE5LgEnuKJs1GdkxWKFucOjcXFf-WTHLwWeF_g=s0-d-e1-ft#https://i.postimg.cc/fbYkFRWv/twitter-alkemy.png\"\n" +
                "                                                                                alt=\"Twitter\" width=\"15\" border=\"0\"\n" +
                "                                                                                style=\"outline:none;border:0px;display:inline-block\"\n" +
                "                                                                                class=\"CToWUd\"></a>&nbsp;<a\n" +
                "                                                                                href=\"https://www.instagram.com/alkemy__/\"\n" +
                "                                                                                style=\"color:rgb(17,85,204)\"\n" +
                "                                                                                target=\"_blank\"\n" +
                "                                                                                data-saferedirecturl=\"https://www.google.com/url?q=https://www.instagram.com/alkemy__/&amp;source=gmail&amp;ust=1651684276372000&amp;usg=AOvVaw1Kb8fD1dlLz7a3pcbAP1ox\"><img\n" +
                "                                                                                src=\"https://ci5.googleusercontent.com/proxy/Vxy401QZZ3apBy9CXS0227xz7Nbwl48VuUCl9X1I12aSfPXYun1ApLftf7yQmRCqsHZAjw6E_VH1TkFW0IQ8CexamrwH=s0-d-e1-ft#https://i.postimg.cc/vZg4rvpk/instagram-alkemy.png\"\n" +
                "                                                                                alt=\"Instagram\" width=\"13\" border=\"0\"\n" +
                "                                                                                style=\"outline:none;border:0px;display:inline-block\"\n" +
                "                                                                                class=\"CToWUd\"></a>&nbsp;<a\n" +
                "                                                                                href=\"https://github.com/alkemy-education\"\n" +
                "                                                                                style=\"color:rgb(17,85,204)\"\n" +
                "                                                                                target=\"_blank\"\n" +
                "                                                                                data-saferedirecturl=\"https://www.google.com/url?q=https://github.com/alkemy-education&amp;source=gmail&amp;ust=1651684276373000&amp;usg=AOvVaw1hBL8tpy3yF-qySncU3DOH\"><img\n" +
                "                                                                                src=\"https://ci5.googleusercontent.com/proxy/vo5PGD6VWafsTjV89jOuuXiZzeIjRf6g11gKA9X0y30Pu_SZ2sTXhxNQWXNZJJdH6CjnPpcsIvRwfxPWtOVwbWCN=s0-d-e1-ft#https://i.postimg.cc/Wb4F3VS2/github-alkemy.png\"\n" +
                "                                                                                alt=\"Git Hub\" width=\"13\" border=\"0\"\n" +
                "                                                                                style=\"outline:none;border:0px;display:inline-block\"\n" +
                "                                                                                class=\"CToWUd\"></a></td>\n" +
                "                                                                    </tr>\n" +
                "                                                                    </tbody>\n" +
                "                                                                </table>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                        </tbody>\n" +
                "                                                    </table>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                        </p>\n" +
                "                                    </div>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <div style=\"height:30px;\">&nbsp;\n" +
                "                                    </div>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                        </table>\n" +
                "\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "\n" +
                "    <div style=\"Margin:0px auto;max-width:600px;\">\n" +
                "\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td style=\"direction:ltr;font-size:0px;padding:9px 0px 9px 0px;text-align:center;vertical-align:top;\">\n" +
                "\n" +
                "                    <div class=\"mj-column-per-100 outlook-group-fix\"\n" +
                "                         style=\"font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\">\n" +
                "\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
                "                               style=\"vertical-align:top;\"\n" +
                "                               width=\"100%\">\n" +
                "\n" +
                "                            <tr>\n" +
                "                                <td style=\"font-size:0px;word-break:break-word;\">\n" +
                "\n" +
                "                                    <div style=\"height:50px;\">&nbsp;\n" +
                "                                    </div>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "\n" +
                "                        </table>\n" +
                "\n" +
                "                    </div>\n" +
                "\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html> ";
    }
}
