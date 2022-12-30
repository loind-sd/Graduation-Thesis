package fpt.lhlvb.softskillcommunity.utils;

public class MailTemplate {

    public static String inviteAdminMail(String role) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <style>\n" +
                "        /* CONFIG STYLES Please do not delete and edit CSS styles below */\n" +
                "/* IMPORTANT THIS STYLES MUST BE ON FINAL EMAIL */\n" +
                "#outlook a {\n" +
                "    padding: 0;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass {\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass,\n" +
                ".ExternalClass p,\n" +
                ".ExternalClass span,\n" +
                ".ExternalClass font,\n" +
                ".ExternalClass td,\n" +
                ".ExternalClass div {\n" +
                "    line-height: 100%;\n" +
                "}\n" +
                "\n" +
                ".es-button {\n" +
                "    mso-style-priority: 100 !important;\n" +
                "    text-decoration: none !important;\n" +
                "}\n" +
                "\n" +
                "a[x-apple-data-detectors] {\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-family: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "}\n" +
                "\n" +
                ".es-desk-hidden {\n" +
                "    display: none;\n" +
                "    float: left;\n" +
                "    overflow: hidden;\n" +
                "    width: 0;\n" +
                "    max-height: 0;\n" +
                "    line-height: 0;\n" +
                "    mso-hide: all;\n" +
                "}\n" +
                "\n" +
                "[data-ogsb] .es-button {\n" +
                "    border-width: 0 !important;\n" +
                "    padding: 10px 20px 10px 20px !important;\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "END OF IMPORTANT\n" +
                "*/\n" +
                "s {\n" +
                "    text-decoration: line-through;\n" +
                "}\n" +
                "\n" +
                "html,\n" +
                "body {\n" +
                "    width: 100%;\n" +
                "    -webkit-text-size-adjust: 100%;\n" +
                "    -ms-text-size-adjust: 100%;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    mso-table-lspace: 0pt;\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    border-collapse: collapse;\n" +
                "    border-spacing: 0px;\n" +
                "}\n" +
                "\n" +
                "table td,\n" +
                "html,\n" +
                "body,\n" +
                ".es-wrapper {\n" +
                "    padding: 0;\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                ".es-content,\n" +
                ".es-header,\n" +
                ".es-footer {\n" +
                "    table-layout: fixed !important;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "img {\n" +
                "    display: block;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "    text-decoration: none;\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "}\n" +
                "\n" +
                "table tr {\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "hr {\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                "h1,\n" +
                "h2,\n" +
                "h3,\n" +
                "h4,\n" +
                "h5 {\n" +
                "    Margin: 0;\n" +
                "    line-height: 120%;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li,\n" +
                "a {\n" +
                "    -webkit-text-size-adjust: none;\n" +
                "    -ms-text-size-adjust: none;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "}\n" +
                "\n" +
                ".es-left {\n" +
                "    float: left;\n" +
                "}\n" +
                "\n" +
                ".es-right {\n" +
                "    float: right;\n" +
                "}\n" +
                "\n" +
                ".es-p5 {\n" +
                "    padding: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5t {\n" +
                "    padding-top: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5b {\n" +
                "    padding-bottom: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5l {\n" +
                "    padding-left: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5r {\n" +
                "    padding-right: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p10 {\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10t {\n" +
                "    padding-top: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10b {\n" +
                "    padding-bottom: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10l {\n" +
                "    padding-left: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10r {\n" +
                "    padding-right: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p15 {\n" +
                "    padding: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15t {\n" +
                "    padding-top: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15b {\n" +
                "    padding-bottom: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15l {\n" +
                "    padding-left: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15r {\n" +
                "    padding-right: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p20 {\n" +
                "    padding: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20t {\n" +
                "    padding-top: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20b {\n" +
                "    padding-bottom: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20l {\n" +
                "    padding-left: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20r {\n" +
                "    padding-right: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p25 {\n" +
                "    padding: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25t {\n" +
                "    padding-top: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25b {\n" +
                "    padding-bottom: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25l {\n" +
                "    padding-left: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25r {\n" +
                "    padding-right: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p30 {\n" +
                "    padding: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30t {\n" +
                "    padding-top: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30b {\n" +
                "    padding-bottom: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30l {\n" +
                "    padding-left: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30r {\n" +
                "    padding-right: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p35 {\n" +
                "    padding: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35t {\n" +
                "    padding-top: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35b {\n" +
                "    padding-bottom: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35l {\n" +
                "    padding-left: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35r {\n" +
                "    padding-right: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p40 {\n" +
                "    padding: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40t {\n" +
                "    padding-top: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40b {\n" +
                "    padding-bottom: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40l {\n" +
                "    padding-left: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40r {\n" +
                "    padding-right: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-menu td {\n" +
                "    border: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a img {\n" +
                "    display: inline-block !important;\n" +
                "}\n" +
                "\n" +
                "/* END CONFIG STYLES */\n" +
                "a {\n" +
                "    text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    line-height: 150%;\n" +
                "}\n" +
                "\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    Margin-bottom: 15px;\n" +
                "    margin-left: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a {\n" +
                "    text-decoration: none;\n" +
                "    display: block;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                ".es-wrapper {\n" +
                "    width: 100%;\n" +
                "    height: 100%;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-wrapper-color,\n" +
                ".es-wrapper {\n" +
                "    background-color: #f6f6f6;\n" +
                "}\n" +
                "\n" +
                ".es-header {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-header-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-header-body p,\n" +
                ".es-header-body ul li,\n" +
                ".es-header-body ol li {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body a {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-content-body p,\n" +
                ".es-content-body ul li,\n" +
                ".es-content-body ol li {\n" +
                "    color: #999999;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body a {\n" +
                "    color: #9aaea6;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-footer {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body {\n" +
                "    background-color: #9aaea6;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body p,\n" +
                ".es-footer-body ul li,\n" +
                ".es-footer-body ol li {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body a {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock,\n" +
                ".es-infoblock p,\n" +
                ".es-infoblock ul li,\n" +
                ".es-infoblock ol li {\n" +
                "    line-height: 120%;\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock a {\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "    font-size: 30px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h2 {\n" +
                "    font-size: 24px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h3 {\n" +
                "    font-size: 20px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h1 a,\n" +
                ".es-content-body h1 a,\n" +
                ".es-footer-body h1 a {\n" +
                "    font-size: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h2 a,\n" +
                ".es-content-body h2 a,\n" +
                ".es-footer-body h2 a {\n" +
                "    font-size: 24px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h3 a,\n" +
                ".es-content-body h3 a,\n" +
                ".es-footer-body h3 a {\n" +
                "    font-size: 20px;\n" +
                "}\n" +
                "\n" +
                "a.es-button,\n" +
                "button.es-button {\n" +
                "    border-style: solid;\n" +
                "    border-color: #9aaea6;\n" +
                "    border-width: 10px 20px 10px 20px;\n" +
                "    display: inline-block;\n" +
                "    background: #9aaea6;\n" +
                "    border-radius: 0px;\n" +
                "    font-size: 18px;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    font-weight: normal;\n" +
                "    font-style: normal;\n" +
                "    line-height: 120%;\n" +
                "    color: #ffffff;\n" +
                "    text-decoration: none;\n" +
                "    width: auto;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".es-button-border {\n" +
                "    border-style: solid solid solid solid;\n" +
                "    border-color: #9aaea6 #9aaea6 #9aaea6 #9aaea6;\n" +
                "    background: #2cb543;\n" +
                "    border-width: 0px 0px 0px 0px;\n" +
                "    display: inline-block;\n" +
                "    border-radius: 0px;\n" +
                "    width: auto;\n" +
                "}\n" +
                "#dobanbiet {font-weight: bold;\n" +
                "    text-decoration: none;\n" +
                "    color: white;\n" +
                "    font-size: 24px;}"+
                "\n" +
                "/* RESPONSIVE STYLES Please do not delete and edit CSS styles below. If you don't need responsive layout, please delete this section. */\n" +
                "@media only screen and (max-width: 600px) {\n" +
                "\n" +
                "    p,\n" +
                "    ul li,\n" +
                "    ol li,\n" +
                "    a {\n" +
                "        line-height: 150% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1,\n" +
                "    h2,\n" +
                "    h3,\n" +
                "    h1 a,\n" +
                "    h2 a,\n" +
                "    h3 a {\n" +
                "        line-height: 120% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "        font-size: 30px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h2 {\n" +
                "        font-size: 26px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h3 {\n" +
                "        font-size: 20px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h1 a,\n" +
                "    .es-content-body h1 a,\n" +
                "    .es-footer-body h1 a {\n" +
                "        font-size: 30px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h2 a,\n" +
                "    .es-content-body h2 a,\n" +
                "    .es-footer-body h2 a {\n" +
                "        font-size: 26px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h3 a,\n" +
                "    .es-content-body h3 a,\n" +
                "    .es-footer-body h3 a {\n" +
                "        font-size: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body p,\n" +
                "    .es-header-body ul li,\n" +
                "    .es-header-body ol li,\n" +
                "    .es-header-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content-body p,\n" +
                "    .es-content-body ul li,\n" +
                "    .es-content-body ol li,\n" +
                "    .es-content-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-footer-body p,\n" +
                "    .es-footer-body ul li,\n" +
                "    .es-footer-body ol li,\n" +
                "    .es-footer-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-infoblock p,\n" +
                "    .es-infoblock ul li,\n" +
                "    .es-infoblock ol li,\n" +
                "    .es-infoblock a {\n" +
                "        font-size: 12px !important;\n" +
                "    }\n" +
                "\n" +
                "    *[class=\"gmail-fix\"] {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-c,\n" +
                "    .es-m-txt-c h1,\n" +
                "    .es-m-txt-c h2,\n" +
                "    .es-m-txt-c h3 {\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r,\n" +
                "    .es-m-txt-r h1,\n" +
                "    .es-m-txt-r h2,\n" +
                "    .es-m-txt-r h3 {\n" +
                "        text-align: right !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-l,\n" +
                "    .es-m-txt-l h1,\n" +
                "    .es-m-txt-l h2,\n" +
                "    .es-m-txt-l h3 {\n" +
                "        text-align: left !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r img,\n" +
                "    .es-m-txt-c img,\n" +
                "    .es-m-txt-l img {\n" +
                "        display: inline !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-button-border {\n" +
                "        display: block !important;\n" +
                "    }\n" +
                "\n" +
                "    a.es-button,\n" +
                "    button.es-button {\n" +
                "        font-size: 20px !important;\n" +
                "        display: block !important;\n" +
                "        border-width: 10px 0px 10px 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-btn-fw {\n" +
                "        border-width: 10px 0px !important;\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adaptive table,\n" +
                "    .es-btn-fw,\n" +
                "    .es-btn-fw-brdr,\n" +
                "    .es-left,\n" +
                "    .es-right {\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content table,\n" +
                "    .es-header table,\n" +
                "    .es-footer table,\n" +
                "    .es-content,\n" +
                "    .es-footer,\n" +
                "    .es-header {\n" +
                "        width: 100% !important;\n" +
                "        max-width: 600px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adapt-td {\n" +
                "        display: block !important;\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .adapt-img {\n" +
                "        width: 100% !important;\n" +
                "        height: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0 {\n" +
                "        padding: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0r {\n" +
                "        padding-right: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0l {\n" +
                "        padding-left: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0t {\n" +
                "        padding-top: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0b {\n" +
                "        padding-bottom: 0 !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p20b {\n" +
                "        padding-bottom: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-mobile-hidden,\n" +
                "    .es-hidden {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden,\n" +
                "    td.es-desk-hidden,\n" +
                "    table.es-desk-hidden {\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        float: none !important;\n" +
                "        max-height: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-desk-hidden {\n" +
                "        display: table !important;\n" +
                "    }\n" +
                "\n" +
                "    td.es-desk-menu-hidden {\n" +
                "        display: table-cell !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td {\n" +
                "        width: 1% !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-table-not-adapt,\n" +
                "    .esd-block-html table {\n" +
                "        width: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social td {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        max-height: inherit !important;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "/* END RESPONSIVE STYLES */\n" +
                "    </style>\n" +
                "    </head>\n" +
                "\n" +
                "<body data-new-gr-c-s-loaded=\"14.1086.0\">\n" +
                "    <div class=\"es-wrapper-color\">\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
                "                        <table class=\"es-content esd-header-popover\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"2432\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" style=\"background-color: #9aaea6;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#9aaea6\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"600\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-banner\" style=\"position: relative;\" align=\"center\" esdev-config=\"h3\"><a target=\"_blank\"><img class=\"adapt-img esdev-stretch-width esdev-banner-rendered\" src=\"https://demo.stripocdn.email/content/guids/bannerImgGuid/images/image16684930668071159.png\" alt=\"Happy Thanksgiving Day\" title=\"Happy Thanksgiving Day\" width=\"600\" style=\"display: block;\"></a>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p30t es-p10b es-p40r es-p40l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"520\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p15t\" align=\"left\">\n" +
                "                                                                                        <p style=\"color: #999999; font-size: 16px; line-height: 150%;\">Chào bạn,<br><br>Quản trị viên trang web chúng tôi đã gửi lời mời hợp tác&nbsp;cho bạn.<br>Khi tham gia, bạn sẽ có tư cách là một <strong>" + role + "</strong>.<br><br>Nếu đồng ý, bạn vui lòng nhấp vào nút Tham gia ở phía dưới.<br>Nếu không, bạn có thể bỏ qua Email này.<br><br>Lưu ý: Lời mời này sẽ hết hạn sau 7 ngày kể từ ngày gửi.<br><br>Trân trọng<br></p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer esd-footer-popover\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"2447\" align=\"center\">\n" +
                "                                        <table class=\"es-footer-body\" style=\"background-color: #9aaea6;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#9aaea6\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p20t es-p20r es-p20l\" align=\"left\">\n" +
                "                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td width=\"560\" class=\"esd-container-frame\" align=\"center\" valign=\"top\">\n" +
                "                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"center\" class=\"esd-block-button h-auto\" valign=\"middle\" height=\"60\"><span class=\"es-button-border\"><a href=\"http://localhost:3000/login\" class=\"es-button\" id=\"dobanbiet\" target=\"_blank\">Tham gia</a></span></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    <div class=\"banner-toolbar\"></div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }
    public static String resetPasswordMail(String pass) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <style>\n" +
                "#outlook a {\n" +
                "    padding: 0;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass {\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass,\n" +
                ".ExternalClass p,\n" +
                ".ExternalClass span,\n" +
                ".ExternalClass font,\n" +
                ".ExternalClass td,\n" +
                ".ExternalClass div {\n" +
                "    line-height: 100%;\n" +
                "}\n" +
                "\n" +
                ".es-button {\n" +
                "    mso-style-priority: 100 !important;\n" +
                "    text-decoration: none !important;\n" +
                "}\n" +
                "\n" +
                "a[x-apple-data-detectors] {\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-family: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "}\n" +
                "\n" +
                ".es-desk-hidden {\n" +
                "    display: none;\n" +
                "    float: left;\n" +
                "    overflow: hidden;\n" +
                "    width: 0;\n" +
                "    max-height: 0;\n" +
                "    line-height: 0;\n" +
                "    mso-hide: all;\n" +
                "}\n" +
                "\n" +
                "[data-ogsb] .es-button {\n" +
                "    border-width: 0 !important;\n" +
                "    padding: 10px 20px 10px 20px !important;\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "END OF IMPORTANT\n" +
                "*/\n" +
                "s {\n" +
                "    text-decoration: line-through;\n" +
                "}\n" +
                "\n" +
                "html,\n" +
                "body {\n" +
                "    width: 100%;\n" +
                "    -webkit-text-size-adjust: 100%;\n" +
                "    -ms-text-size-adjust: 100%;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    mso-table-lspace: 0pt;\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    border-collapse: collapse;\n" +
                "    border-spacing: 0px;\n" +
                "}\n" +
                "\n" +
                "table td,\n" +
                "html,\n" +
                "body,\n" +
                ".es-wrapper {\n" +
                "    padding: 0;\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                ".es-content,\n" +
                ".es-header,\n" +
                ".es-footer {\n" +
                "    table-layout: fixed !important;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "img {\n" +
                "    display: block;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "    text-decoration: none;\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "}\n" +
                "\n" +
                "table tr {\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "hr {\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                "h1,\n" +
                "h2,\n" +
                "h3,\n" +
                "h4,\n" +
                "h5 {\n" +
                "    Margin: 0;\n" +
                "    line-height: 120%;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li,\n" +
                "a {\n" +
                "    -webkit-text-size-adjust: none;\n" +
                "    -ms-text-size-adjust: none;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "}\n" +
                "\n" +
                ".es-left {\n" +
                "    float: left;\n" +
                "}\n" +
                "\n" +
                ".es-right {\n" +
                "    float: right;\n" +
                "}\n" +
                "\n" +
                ".es-p5 {\n" +
                "    padding: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5t {\n" +
                "    padding-top: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5b {\n" +
                "    padding-bottom: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5l {\n" +
                "    padding-left: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5r {\n" +
                "    padding-right: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p10 {\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10t {\n" +
                "    padding-top: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10b {\n" +
                "    padding-bottom: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10l {\n" +
                "    padding-left: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10r {\n" +
                "    padding-right: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p15 {\n" +
                "    padding: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15t {\n" +
                "    padding-top: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15b {\n" +
                "    padding-bottom: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15l {\n" +
                "    padding-left: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15r {\n" +
                "    padding-right: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p20 {\n" +
                "    padding: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20t {\n" +
                "    padding-top: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20b {\n" +
                "    padding-bottom: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20l {\n" +
                "    padding-left: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20r {\n" +
                "    padding-right: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p25 {\n" +
                "    padding: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25t {\n" +
                "    padding-top: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25b {\n" +
                "    padding-bottom: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25l {\n" +
                "    padding-left: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25r {\n" +
                "    padding-right: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p30 {\n" +
                "    padding: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30t {\n" +
                "    padding-top: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30b {\n" +
                "    padding-bottom: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30l {\n" +
                "    padding-left: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30r {\n" +
                "    padding-right: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p35 {\n" +
                "    padding: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35t {\n" +
                "    padding-top: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35b {\n" +
                "    padding-bottom: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35l {\n" +
                "    padding-left: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35r {\n" +
                "    padding-right: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p40 {\n" +
                "    padding: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40t {\n" +
                "    padding-top: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40b {\n" +
                "    padding-bottom: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40l {\n" +
                "    padding-left: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40r {\n" +
                "    padding-right: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-menu td {\n" +
                "    border: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a img {\n" +
                "    display: inline-block !important;\n" +
                "}\n" +
                "\n" +
                "/* END CONFIG STYLES */\n" +
                "a {\n" +
                "    text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    line-height: 150%;\n" +
                "}\n" +
                "\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    Margin-bottom: 15px;\n" +
                "    margin-left: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a {\n" +
                "    text-decoration: none;\n" +
                "    display: block;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                ".es-wrapper {\n" +
                "    width: 100%;\n" +
                "    height: 100%;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-wrapper-color,\n" +
                ".es-wrapper {\n" +
                "    background-color: #f6f6f6;\n" +
                "}\n" +
                "\n" +
                ".es-header {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-header-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-header-body p,\n" +
                ".es-header-body ul li,\n" +
                ".es-header-body ol li {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body a {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-content-body p,\n" +
                ".es-content-body ul li,\n" +
                ".es-content-body ol li {\n" +
                "    color: #999999;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body a {\n" +
                "    color: #9aaea6;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-footer {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body {\n" +
                "    background-color: #9aaea6;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body p,\n" +
                ".es-footer-body ul li,\n" +
                ".es-footer-body ol li {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body a {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock,\n" +
                ".es-infoblock p,\n" +
                ".es-infoblock ul li,\n" +
                ".es-infoblock ol li {\n" +
                "    line-height: 120%;\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock a {\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "    font-size: 30px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h2 {\n" +
                "    font-size: 24px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h3 {\n" +
                "    font-size: 20px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h1 a,\n" +
                ".es-content-body h1 a,\n" +
                ".es-footer-body h1 a {\n" +
                "    font-size: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h2 a,\n" +
                ".es-content-body h2 a,\n" +
                ".es-footer-body h2 a {\n" +
                "    font-size: 24px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h3 a,\n" +
                ".es-content-body h3 a,\n" +
                ".es-footer-body h3 a {\n" +
                "    font-size: 20px;\n" +
                "}\n" +
                "\n" +
                "a.es-button,\n" +
                "button.es-button {\n" +
                "    border-style: solid;\n" +
                "    border-color: #9aaea6;\n" +
                "    border-width: 10px 20px 10px 20px;\n" +
                "    display: inline-block;\n" +
                "    background: #9aaea6;\n" +
                "    border-radius: 0px;\n" +
                "    font-size: 18px;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    font-weight: normal;\n" +
                "    font-style: normal;\n" +
                "    line-height: 120%;\n" +
                "    color: #ffffff;\n" +
                "    text-decoration: none;\n" +
                "    width: auto;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".es-button-border {\n" +
                "    border-style: solid solid solid solid;\n" +
                "    border-color: #9aaea6 #9aaea6 #9aaea6 #9aaea6;\n" +
                "    background: #2cb543;\n" +
                "    border-width: 0px 0px 0px 0px;\n" +
                "    display: inline-block;\n" +
                "    border-radius: 0px;\n" +
                "    width: auto;\n" +
                "}\n" +
                "\n" +
                "/* RESPONSIVE STYLES Please do not delete and edit CSS styles below. If you don't need responsive layout, please delete this section. */\n" +
                "@media only screen and (max-width: 600px) {\n" +
                "\n" +
                "    p,\n" +
                "    ul li,\n" +
                "    ol li,\n" +
                "    a {\n" +
                "        line-height: 150% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1,\n" +
                "    h2,\n" +
                "    h3,\n" +
                "    h1 a,\n" +
                "    h2 a,\n" +
                "    h3 a {\n" +
                "        line-height: 120% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "        font-size: 30px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h2 {\n" +
                "        font-size: 26px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h3 {\n" +
                "        font-size: 20px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h1 a,\n" +
                "    .es-content-body h1 a,\n" +
                "    .es-footer-body h1 a {\n" +
                "        font-size: 30px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h2 a,\n" +
                "    .es-content-body h2 a,\n" +
                "    .es-footer-body h2 a {\n" +
                "        font-size: 26px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h3 a,\n" +
                "    .es-content-body h3 a,\n" +
                "    .es-footer-body h3 a {\n" +
                "        font-size: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body p,\n" +
                "    .es-header-body ul li,\n" +
                "    .es-header-body ol li,\n" +
                "    .es-header-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content-body p,\n" +
                "    .es-content-body ul li,\n" +
                "    .es-content-body ol li,\n" +
                "    .es-content-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-footer-body p,\n" +
                "    .es-footer-body ul li,\n" +
                "    .es-footer-body ol li,\n" +
                "    .es-footer-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-infoblock p,\n" +
                "    .es-infoblock ul li,\n" +
                "    .es-infoblock ol li,\n" +
                "    .es-infoblock a {\n" +
                "        font-size: 12px !important;\n" +
                "    }\n" +
                "\n" +
                "    *[class=\"gmail-fix\"] {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-c,\n" +
                "    .es-m-txt-c h1,\n" +
                "    .es-m-txt-c h2,\n" +
                "    .es-m-txt-c h3 {\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r,\n" +
                "    .es-m-txt-r h1,\n" +
                "    .es-m-txt-r h2,\n" +
                "    .es-m-txt-r h3 {\n" +
                "        text-align: right !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-l,\n" +
                "    .es-m-txt-l h1,\n" +
                "    .es-m-txt-l h2,\n" +
                "    .es-m-txt-l h3 {\n" +
                "        text-align: left !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r img,\n" +
                "    .es-m-txt-c img,\n" +
                "    .es-m-txt-l img {\n" +
                "        display: inline !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-button-border {\n" +
                "        display: block !important;\n" +
                "    }\n" +
                "\n" +
                "    a.es-button,\n" +
                "    button.es-button {\n" +
                "        font-size: 20px !important;\n" +
                "        display: block !important;\n" +
                "        border-width: 10px 0px 10px 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-btn-fw {\n" +
                "        border-width: 10px 0px !important;\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adaptive table,\n" +
                "    .es-btn-fw,\n" +
                "    .es-btn-fw-brdr,\n" +
                "    .es-left,\n" +
                "    .es-right {\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content table,\n" +
                "    .es-header table,\n" +
                "    .es-footer table,\n" +
                "    .es-content,\n" +
                "    .es-footer,\n" +
                "    .es-header {\n" +
                "        width: 100% !important;\n" +
                "        max-width: 600px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adapt-td {\n" +
                "        display: block !important;\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .adapt-img {\n" +
                "        width: 100% !important;\n" +
                "        height: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0 {\n" +
                "        padding: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0r {\n" +
                "        padding-right: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0l {\n" +
                "        padding-left: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0t {\n" +
                "        padding-top: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0b {\n" +
                "        padding-bottom: 0 !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p20b {\n" +
                "        padding-bottom: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-mobile-hidden,\n" +
                "    .es-hidden {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden,\n" +
                "    td.es-desk-hidden,\n" +
                "    table.es-desk-hidden {\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        float: none !important;\n" +
                "        max-height: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-desk-hidden {\n" +
                "        display: table !important;\n" +
                "    }\n" +
                "\n" +
                "    td.es-desk-menu-hidden {\n" +
                "        display: table-cell !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td {\n" +
                "        width: 1% !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-table-not-adapt,\n" +
                "    .esd-block-html table {\n" +
                "        width: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social td {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        max-height: inherit !important;\n" +
                "    }\n" +
                "}" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body data-new-gr-c-s-loaded=\"14.1086.0\">\n" +
                "    <div class=\"es-wrapper-color\">\n" +
                "        <!--[if gte mso 9]>\n" +
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n" +
                "</v:background>\n" +
                "<![endif]-->\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
                "                        <table class=\"es-content esd-footer-popover\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p30t es-p10b es-p40r es-p40l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"520\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p15t\" align=\"left\">\n" +
                "                                                                                        <p style=\"color: #999999; font-size: 16px; line-height: 150%; font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\">Chào bạn,<br><br><em>** Đây là tin nhắn tự động - vui lòng không trả lời vì bạn sẽ không nhận được phản hồi. **</em><br><br>Thông báo này được gửi đến do đã có&nbsp;yêu cầu đặt lại mật khẩu tài khoản của bạn. Vui lòng nhấp vào liên kết bên dưới và làm theo hướng dẫn để thay đổi mật khẩu của bạn.<br><br>Mật khẩu mới của bạn là: <strong>" + pass + "</strong><br><br><a href=\"https://chgpwd.fpt.edu.vn/\" target=\"_blank\">https://chgpwd.fpt.edu.vn</a><br><br>Thân gửi</p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    public static String  sendInitPasswordMail(String pass) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <style>\n" +
                "#outlook a {\n" +
                "    padding: 0;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass {\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass,\n" +
                ".ExternalClass p,\n" +
                ".ExternalClass span,\n" +
                ".ExternalClass font,\n" +
                ".ExternalClass td,\n" +
                ".ExternalClass div {\n" +
                "    line-height: 100%;\n" +
                "}\n" +
                "\n" +
                ".es-button {\n" +
                "    mso-style-priority: 100 !important;\n" +
                "    text-decoration: none !important;\n" +
                "}\n" +
                "\n" +
                "a[x-apple-data-detectors] {\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-family: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "}\n" +
                "\n" +
                ".es-desk-hidden {\n" +
                "    display: none;\n" +
                "    float: left;\n" +
                "    overflow: hidden;\n" +
                "    width: 0;\n" +
                "    max-height: 0;\n" +
                "    line-height: 0;\n" +
                "    mso-hide: all;\n" +
                "}\n" +
                "\n" +
                "[data-ogsb] .es-button {\n" +
                "    border-width: 0 !important;\n" +
                "    padding: 10px 20px 10px 20px !important;\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "END OF IMPORTANT\n" +
                "*/\n" +
                "s {\n" +
                "    text-decoration: line-through;\n" +
                "}\n" +
                "\n" +
                "html,\n" +
                "body {\n" +
                "    width: 100%;\n" +
                "    -webkit-text-size-adjust: 100%;\n" +
                "    -ms-text-size-adjust: 100%;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    mso-table-lspace: 0pt;\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    border-collapse: collapse;\n" +
                "    border-spacing: 0px;\n" +
                "}\n" +
                "\n" +
                "table td,\n" +
                "html,\n" +
                "body,\n" +
                ".es-wrapper {\n" +
                "    padding: 0;\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                ".es-content,\n" +
                ".es-header,\n" +
                ".es-footer {\n" +
                "    table-layout: fixed !important;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "img {\n" +
                "    display: block;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "    text-decoration: none;\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "}\n" +
                "\n" +
                "table tr {\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "hr {\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                "h1,\n" +
                "h2,\n" +
                "h3,\n" +
                "h4,\n" +
                "h5 {\n" +
                "    Margin: 0;\n" +
                "    line-height: 120%;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li,\n" +
                "a {\n" +
                "    -webkit-text-size-adjust: none;\n" +
                "    -ms-text-size-adjust: none;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "}\n" +
                "\n" +
                ".es-left {\n" +
                "    float: left;\n" +
                "}\n" +
                "\n" +
                ".es-right {\n" +
                "    float: right;\n" +
                "}\n" +
                "\n" +
                ".es-p5 {\n" +
                "    padding: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5t {\n" +
                "    padding-top: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5b {\n" +
                "    padding-bottom: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5l {\n" +
                "    padding-left: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5r {\n" +
                "    padding-right: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p10 {\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10t {\n" +
                "    padding-top: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10b {\n" +
                "    padding-bottom: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10l {\n" +
                "    padding-left: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10r {\n" +
                "    padding-right: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p15 {\n" +
                "    padding: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15t {\n" +
                "    padding-top: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15b {\n" +
                "    padding-bottom: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15l {\n" +
                "    padding-left: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15r {\n" +
                "    padding-right: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p20 {\n" +
                "    padding: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20t {\n" +
                "    padding-top: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20b {\n" +
                "    padding-bottom: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20l {\n" +
                "    padding-left: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20r {\n" +
                "    padding-right: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p25 {\n" +
                "    padding: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25t {\n" +
                "    padding-top: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25b {\n" +
                "    padding-bottom: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25l {\n" +
                "    padding-left: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25r {\n" +
                "    padding-right: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p30 {\n" +
                "    padding: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30t {\n" +
                "    padding-top: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30b {\n" +
                "    padding-bottom: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30l {\n" +
                "    padding-left: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30r {\n" +
                "    padding-right: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p35 {\n" +
                "    padding: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35t {\n" +
                "    padding-top: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35b {\n" +
                "    padding-bottom: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35l {\n" +
                "    padding-left: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35r {\n" +
                "    padding-right: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p40 {\n" +
                "    padding: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40t {\n" +
                "    padding-top: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40b {\n" +
                "    padding-bottom: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40l {\n" +
                "    padding-left: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40r {\n" +
                "    padding-right: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-menu td {\n" +
                "    border: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a img {\n" +
                "    display: inline-block !important;\n" +
                "}\n" +
                "\n" +
                "/* END CONFIG STYLES */\n" +
                "a {\n" +
                "    text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    line-height: 150%;\n" +
                "}\n" +
                "\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    Margin-bottom: 15px;\n" +
                "    margin-left: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a {\n" +
                "    text-decoration: none;\n" +
                "    display: block;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                ".es-wrapper {\n" +
                "    width: 100%;\n" +
                "    height: 100%;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-wrapper-color,\n" +
                ".es-wrapper {\n" +
                "    background-color: #f6f6f6;\n" +
                "}\n" +
                "\n" +
                ".es-header {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-header-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-header-body p,\n" +
                ".es-header-body ul li,\n" +
                ".es-header-body ol li {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body a {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-content-body p,\n" +
                ".es-content-body ul li,\n" +
                ".es-content-body ol li {\n" +
                "    color: #999999;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body a {\n" +
                "    color: #9aaea6;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-footer {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body {\n" +
                "    background-color: #9aaea6;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body p,\n" +
                ".es-footer-body ul li,\n" +
                ".es-footer-body ol li {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body a {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock,\n" +
                ".es-infoblock p,\n" +
                ".es-infoblock ul li,\n" +
                ".es-infoblock ol li {\n" +
                "    line-height: 120%;\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock a {\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "    font-size: 30px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h2 {\n" +
                "    font-size: 24px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h3 {\n" +
                "    font-size: 20px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h1 a,\n" +
                ".es-content-body h1 a,\n" +
                ".es-footer-body h1 a {\n" +
                "    font-size: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h2 a,\n" +
                ".es-content-body h2 a,\n" +
                ".es-footer-body h2 a {\n" +
                "    font-size: 24px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h3 a,\n" +
                ".es-content-body h3 a,\n" +
                ".es-footer-body h3 a {\n" +
                "    font-size: 20px;\n" +
                "}\n" +
                "\n" +
                "a.es-button,\n" +
                "button.es-button {\n" +
                "    border-style: solid;\n" +
                "    border-color: #9aaea6;\n" +
                "    border-width: 10px 20px 10px 20px;\n" +
                "    display: inline-block;\n" +
                "    background: #9aaea6;\n" +
                "    border-radius: 0px;\n" +
                "    font-size: 18px;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    font-weight: normal;\n" +
                "    font-style: normal;\n" +
                "    line-height: 120%;\n" +
                "    color: #ffffff;\n" +
                "    text-decoration: none;\n" +
                "    width: auto;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".es-button-border {\n" +
                "    border-style: solid solid solid solid;\n" +
                "    border-color: #9aaea6 #9aaea6 #9aaea6 #9aaea6;\n" +
                "    background: #2cb543;\n" +
                "    border-width: 0px 0px 0px 0px;\n" +
                "    display: inline-block;\n" +
                "    border-radius: 0px;\n" +
                "    width: auto;\n" +
                "}\n" +
                "\n" +
                "/* RESPONSIVE STYLES Please do not delete and edit CSS styles below. If you don't need responsive layout, please delete this section. */\n" +
                "@media only screen and (max-width: 600px) {\n" +
                "\n" +
                "    p,\n" +
                "    ul li,\n" +
                "    ol li,\n" +
                "    a {\n" +
                "        line-height: 150% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1,\n" +
                "    h2,\n" +
                "    h3,\n" +
                "    h1 a,\n" +
                "    h2 a,\n" +
                "    h3 a {\n" +
                "        line-height: 120% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "        font-size: 30px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h2 {\n" +
                "        font-size: 26px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h3 {\n" +
                "        font-size: 20px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h1 a,\n" +
                "    .es-content-body h1 a,\n" +
                "    .es-footer-body h1 a {\n" +
                "        font-size: 30px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h2 a,\n" +
                "    .es-content-body h2 a,\n" +
                "    .es-footer-body h2 a {\n" +
                "        font-size: 26px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h3 a,\n" +
                "    .es-content-body h3 a,\n" +
                "    .es-footer-body h3 a {\n" +
                "        font-size: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body p,\n" +
                "    .es-header-body ul li,\n" +
                "    .es-header-body ol li,\n" +
                "    .es-header-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content-body p,\n" +
                "    .es-content-body ul li,\n" +
                "    .es-content-body ol li,\n" +
                "    .es-content-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-footer-body p,\n" +
                "    .es-footer-body ul li,\n" +
                "    .es-footer-body ol li,\n" +
                "    .es-footer-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-infoblock p,\n" +
                "    .es-infoblock ul li,\n" +
                "    .es-infoblock ol li,\n" +
                "    .es-infoblock a {\n" +
                "        font-size: 12px !important;\n" +
                "    }\n" +
                "\n" +
                "    *[class=\"gmail-fix\"] {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-c,\n" +
                "    .es-m-txt-c h1,\n" +
                "    .es-m-txt-c h2,\n" +
                "    .es-m-txt-c h3 {\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r,\n" +
                "    .es-m-txt-r h1,\n" +
                "    .es-m-txt-r h2,\n" +
                "    .es-m-txt-r h3 {\n" +
                "        text-align: right !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-l,\n" +
                "    .es-m-txt-l h1,\n" +
                "    .es-m-txt-l h2,\n" +
                "    .es-m-txt-l h3 {\n" +
                "        text-align: left !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r img,\n" +
                "    .es-m-txt-c img,\n" +
                "    .es-m-txt-l img {\n" +
                "        display: inline !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-button-border {\n" +
                "        display: block !important;\n" +
                "    }\n" +
                "\n" +
                "    a.es-button,\n" +
                "    button.es-button {\n" +
                "        font-size: 20px !important;\n" +
                "        display: block !important;\n" +
                "        border-width: 10px 0px 10px 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-btn-fw {\n" +
                "        border-width: 10px 0px !important;\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adaptive table,\n" +
                "    .es-btn-fw,\n" +
                "    .es-btn-fw-brdr,\n" +
                "    .es-left,\n" +
                "    .es-right {\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content table,\n" +
                "    .es-header table,\n" +
                "    .es-footer table,\n" +
                "    .es-content,\n" +
                "    .es-footer,\n" +
                "    .es-header {\n" +
                "        width: 100% !important;\n" +
                "        max-width: 600px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adapt-td {\n" +
                "        display: block !important;\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .adapt-img {\n" +
                "        width: 100% !important;\n" +
                "        height: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0 {\n" +
                "        padding: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0r {\n" +
                "        padding-right: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0l {\n" +
                "        padding-left: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0t {\n" +
                "        padding-top: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0b {\n" +
                "        padding-bottom: 0 !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p20b {\n" +
                "        padding-bottom: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-mobile-hidden,\n" +
                "    .es-hidden {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden,\n" +
                "    td.es-desk-hidden,\n" +
                "    table.es-desk-hidden {\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        float: none !important;\n" +
                "        max-height: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-desk-hidden {\n" +
                "        display: table !important;\n" +
                "    }\n" +
                "\n" +
                "    td.es-desk-menu-hidden {\n" +
                "        display: table-cell !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td {\n" +
                "        width: 1% !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-table-not-adapt,\n" +
                "    .esd-block-html table {\n" +
                "        width: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social td {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        max-height: inherit !important;\n" +
                "    }\n" +
                "}" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body data-new-gr-c-s-loaded=\"14.1086.0\">\n" +
                "    <div class=\"es-wrapper-color\">\n" +
                "        <!--[if gte mso 9]>\n" +
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n" +
                "</v:background>\n" +
                "<![endif]-->\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
                "                        <table class=\"es-content esd-footer-popover\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p30t es-p10b es-p40r es-p40l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"520\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p15t\" align=\"left\">\n" +
                "                                                                                        <p style=\"color: #999999; font-size: 16px; line-height: 150%; font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\">Chào bạn,<br><br><em>** Đây là tin nhắn tự động - vui lòng không trả lời vì bạn sẽ không nhận được phản hồi. **</em><br><br>Thông báo này được gửi đến nhằm cung cấp mật khẩu cho bạn. Bạn có thể nhấp vào liên kết bên dưới để đăng nhập và cùng trải nghiệm những bài học quý giá cùng những người bạn.<br><br>Mật khẩu mới của bạn là: <strong>" + pass + "</strong><br><br><a href=\"https://chgpwd.fpt.edu.vn/\" target=\"_blank\">https://chgpwd.fpt.edu.vn</a><br><br>Thân gửi</p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }
    public static String getMailTemplateThank(String mailContent) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <style>\n" +
                "#outlook a {\n" +
                "    padding: 0;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass {\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass,\n" +
                ".ExternalClass p,\n" +
                ".ExternalClass span,\n" +
                ".ExternalClass font,\n" +
                ".ExternalClass td,\n" +
                ".ExternalClass div {\n" +
                "    line-height: 100%;\n" +
                "}\n" +
                "\n" +
                ".es-button {\n" +
                "    mso-style-priority: 100 !important;\n" +
                "    text-decoration: none !important;\n" +
                "}\n" +
                "\n" +
                "a[x-apple-data-detectors] {\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-family: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "}\n" +
                "\n" +
                ".es-desk-hidden {\n" +
                "    display: none;\n" +
                "    float: left;\n" +
                "    overflow: hidden;\n" +
                "    width: 0;\n" +
                "    max-height: 0;\n" +
                "    line-height: 0;\n" +
                "    mso-hide: all;\n" +
                "}\n" +
                "\n" +
                "[data-ogsb] .es-button {\n" +
                "    border-width: 0 !important;\n" +
                "    padding: 10px 20px 10px 20px !important;\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "END OF IMPORTANT\n" +
                "*/\n" +
                "s {\n" +
                "    text-decoration: line-through;\n" +
                "}\n" +
                "\n" +
                "html,\n" +
                "body {\n" +
                "    width: 100%;\n" +
                "    -webkit-text-size-adjust: 100%;\n" +
                "    -ms-text-size-adjust: 100%;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    mso-table-lspace: 0pt;\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    border-collapse: collapse;\n" +
                "    border-spacing: 0px;\n" +
                "}\n" +
                "\n" +
                "table td,\n" +
                "html,\n" +
                "body,\n" +
                ".es-wrapper {\n" +
                "    padding: 0;\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                ".es-content,\n" +
                ".es-header,\n" +
                ".es-footer {\n" +
                "    table-layout: fixed !important;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "img {\n" +
                "    display: block;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "    text-decoration: none;\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "}\n" +
                "\n" +
                "table tr {\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "hr {\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                "h1,\n" +
                "h2,\n" +
                "h3,\n" +
                "h4,\n" +
                "h5 {\n" +
                "    Margin: 0;\n" +
                "    line-height: 120%;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li,\n" +
                "a {\n" +
                "    -webkit-text-size-adjust: none;\n" +
                "    -ms-text-size-adjust: none;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "}\n" +
                "\n" +
                ".es-left {\n" +
                "    float: left;\n" +
                "}\n" +
                "\n" +
                ".es-right {\n" +
                "    float: right;\n" +
                "}\n" +
                "\n" +
                ".es-p5 {\n" +
                "    padding: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5t {\n" +
                "    padding-top: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5b {\n" +
                "    padding-bottom: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5l {\n" +
                "    padding-left: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5r {\n" +
                "    padding-right: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p10 {\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10t {\n" +
                "    padding-top: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10b {\n" +
                "    padding-bottom: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10l {\n" +
                "    padding-left: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10r {\n" +
                "    padding-right: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p15 {\n" +
                "    padding: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15t {\n" +
                "    padding-top: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15b {\n" +
                "    padding-bottom: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15l {\n" +
                "    padding-left: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15r {\n" +
                "    padding-right: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p20 {\n" +
                "    padding: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20t {\n" +
                "    padding-top: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20b {\n" +
                "    padding-bottom: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20l {\n" +
                "    padding-left: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20r {\n" +
                "    padding-right: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p25 {\n" +
                "    padding: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25t {\n" +
                "    padding-top: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25b {\n" +
                "    padding-bottom: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25l {\n" +
                "    padding-left: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25r {\n" +
                "    padding-right: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p30 {\n" +
                "    padding: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30t {\n" +
                "    padding-top: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30b {\n" +
                "    padding-bottom: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30l {\n" +
                "    padding-left: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30r {\n" +
                "    padding-right: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p35 {\n" +
                "    padding: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35t {\n" +
                "    padding-top: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35b {\n" +
                "    padding-bottom: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35l {\n" +
                "    padding-left: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35r {\n" +
                "    padding-right: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p40 {\n" +
                "    padding: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40t {\n" +
                "    padding-top: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40b {\n" +
                "    padding-bottom: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40l {\n" +
                "    padding-left: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40r {\n" +
                "    padding-right: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-menu td {\n" +
                "    border: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a img {\n" +
                "    display: inline-block !important;\n" +
                "}\n" +
                "\n" +
                "/* END CONFIG STYLES */\n" +
                "a {\n" +
                "    text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    line-height: 150%;\n" +
                "}\n" +
                "\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    Margin-bottom: 15px;\n" +
                "    margin-left: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a {\n" +
                "    text-decoration: none;\n" +
                "    display: block;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "}\n" +
                "\n" +
                ".es-wrapper {\n" +
                "    width: 100%;\n" +
                "    height: 100%;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-wrapper-color,\n" +
                ".es-wrapper {\n" +
                "    background-color: #f6f6f6;\n" +
                "}\n" +
                "\n" +
                ".es-header {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-header-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-header-body p,\n" +
                ".es-header-body ul li,\n" +
                ".es-header-body ol li {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body a {\n" +
                "    color: #cccccc;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body {\n" +
                "    background-color: #ffffff;\n" +
                "}\n" +
                "\n" +
                ".es-content-body p,\n" +
                ".es-content-body ul li,\n" +
                ".es-content-body ol li {\n" +
                "    color: #999999;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body a {\n" +
                "    color: #9aaea6;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-footer {\n" +
                "    background-color: transparent;\n" +
                "    background-image: ;\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body {\n" +
                "    background-color: #9aaea6;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body p,\n" +
                ".es-footer-body ul li,\n" +
                ".es-footer-body ol li {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body a {\n" +
                "    color: #666666;\n" +
                "    font-size: 12px;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock,\n" +
                ".es-infoblock p,\n" +
                ".es-infoblock ul li,\n" +
                ".es-infoblock ol li {\n" +
                "    line-height: 120%;\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock a {\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "    font-size: 30px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h2 {\n" +
                "    font-size: 24px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                "h3 {\n" +
                "    font-size: 20px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #666666;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h1 a,\n" +
                ".es-content-body h1 a,\n" +
                ".es-footer-body h1 a {\n" +
                "    font-size: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h2 a,\n" +
                ".es-content-body h2 a,\n" +
                ".es-footer-body h2 a {\n" +
                "    font-size: 24px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h3 a,\n" +
                ".es-content-body h3 a,\n" +
                ".es-footer-body h3 a {\n" +
                "    font-size: 20px;\n" +
                "}\n" +
                "\n" +
                "a.es-button,\n" +
                "button.es-button {\n" +
                "    border-style: solid;\n" +
                "    border-color: #9aaea6;\n" +
                "    border-width: 10px 20px 10px 20px;\n" +
                "    display: inline-block;\n" +
                "    background: #9aaea6;\n" +
                "    border-radius: 0px;\n" +
                "    font-size: 18px;\n" +
                "    font-family: 'arial', 'helvetica neue', 'helvetica', 'sans-serif';\n" +
                "    font-weight: normal;\n" +
                "    font-style: normal;\n" +
                "    line-height: 120%;\n" +
                "    color: #ffffff;\n" +
                "    text-decoration: none;\n" +
                "    width: auto;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".es-button-border {\n" +
                "    border-style: solid solid solid solid;\n" +
                "    border-color: #9aaea6 #9aaea6 #9aaea6 #9aaea6;\n" +
                "    background: #2cb543;\n" +
                "    border-width: 0px 0px 0px 0px;\n" +
                "    display: inline-block;\n" +
                "    border-radius: 0px;\n" +
                "    width: auto;\n" +
                "}\n" +
                "\n" +
                "/* RESPONSIVE STYLES Please do not delete and edit CSS styles below. If you don't need responsive layout, please delete this section. */\n" +
                "@media only screen and (max-width: 600px) {\n" +
                "\n" +
                "    p,\n" +
                "    ul li,\n" +
                "    ol li,\n" +
                "    a {\n" +
                "        line-height: 150% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1,\n" +
                "    h2,\n" +
                "    h3,\n" +
                "    h1 a,\n" +
                "    h2 a,\n" +
                "    h3 a {\n" +
                "        line-height: 120% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "        font-size: 30px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h2 {\n" +
                "        font-size: 26px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h3 {\n" +
                "        font-size: 20px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h1 a,\n" +
                "    .es-content-body h1 a,\n" +
                "    .es-footer-body h1 a {\n" +
                "        font-size: 30px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h2 a,\n" +
                "    .es-content-body h2 a,\n" +
                "    .es-footer-body h2 a {\n" +
                "        font-size: 26px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h3 a,\n" +
                "    .es-content-body h3 a,\n" +
                "    .es-footer-body h3 a {\n" +
                "        font-size: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body p,\n" +
                "    .es-header-body ul li,\n" +
                "    .es-header-body ol li,\n" +
                "    .es-header-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content-body p,\n" +
                "    .es-content-body ul li,\n" +
                "    .es-content-body ol li,\n" +
                "    .es-content-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-footer-body p,\n" +
                "    .es-footer-body ul li,\n" +
                "    .es-footer-body ol li,\n" +
                "    .es-footer-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-infoblock p,\n" +
                "    .es-infoblock ul li,\n" +
                "    .es-infoblock ol li,\n" +
                "    .es-infoblock a {\n" +
                "        font-size: 12px !important;\n" +
                "    }\n" +
                "\n" +
                "    *[class=\"gmail-fix\"] {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-c,\n" +
                "    .es-m-txt-c h1,\n" +
                "    .es-m-txt-c h2,\n" +
                "    .es-m-txt-c h3 {\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r,\n" +
                "    .es-m-txt-r h1,\n" +
                "    .es-m-txt-r h2,\n" +
                "    .es-m-txt-r h3 {\n" +
                "        text-align: right !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-l,\n" +
                "    .es-m-txt-l h1,\n" +
                "    .es-m-txt-l h2,\n" +
                "    .es-m-txt-l h3 {\n" +
                "        text-align: left !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r img,\n" +
                "    .es-m-txt-c img,\n" +
                "    .es-m-txt-l img {\n" +
                "        display: inline !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-button-border {\n" +
                "        display: block !important;\n" +
                "    }\n" +
                "\n" +
                "    a.es-button,\n" +
                "    button.es-button {\n" +
                "        font-size: 20px !important;\n" +
                "        display: block !important;\n" +
                "        border-width: 10px 0px 10px 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-btn-fw {\n" +
                "        border-width: 10px 0px !important;\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adaptive table,\n" +
                "    .es-btn-fw,\n" +
                "    .es-btn-fw-brdr,\n" +
                "    .es-left,\n" +
                "    .es-right {\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content table,\n" +
                "    .es-header table,\n" +
                "    .es-footer table,\n" +
                "    .es-content,\n" +
                "    .es-footer,\n" +
                "    .es-header {\n" +
                "        width: 100% !important;\n" +
                "        max-width: 600px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adapt-td {\n" +
                "        display: block !important;\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .adapt-img {\n" +
                "        width: 100% !important;\n" +
                "        height: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0 {\n" +
                "        padding: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0r {\n" +
                "        padding-right: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0l {\n" +
                "        padding-left: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0t {\n" +
                "        padding-top: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0b {\n" +
                "        padding-bottom: 0 !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p20b {\n" +
                "        padding-bottom: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-mobile-hidden,\n" +
                "    .es-hidden {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden,\n" +
                "    td.es-desk-hidden,\n" +
                "    table.es-desk-hidden {\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        float: none !important;\n" +
                "        max-height: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-desk-hidden {\n" +
                "        display: table !important;\n" +
                "    }\n" +
                "\n" +
                "    td.es-desk-menu-hidden {\n" +
                "        display: table-cell !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td {\n" +
                "        width: 1% !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-table-not-adapt,\n" +
                "    .esd-block-html table {\n" +
                "        width: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social td {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        max-height: inherit !important;\n" +
                "    }\n" +
                "}\n" +
                "</style>\n" +
                "    \n" +
                "</head>\n" +
                "\n" +
                "<body data-new-gr-c-s-loaded=\"14.1086.0\">\n" +
                "    <div class=\"es-wrapper-color\">\n" +
                "        <!--[if gte mso 9]>\n" +
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n" +
                "</v:background>\n" +
                "<![endif]-->\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
                "                        <table class=\"es-content esd-header-popover\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"2432\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" style=\"background-color: #9aaea6;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#9aaea6\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"600\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-banner\" style=\"position: relative;\" align=\"center\" esdev-config=\"h1\"><a target=\"_blank\"><img class=\"adapt-img esdev-stretch-width esdev-banner-rendered\" src=\"https://demo.stripocdn.email/content/guids/bannerImgGuid/images/image16682189789554503.png\" alt=\"Happy Thanksgiving Day\" title=\"Happy Thanksgiving Day\" width=\"100%\"></a></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table class=\"es-content esd-footer-popover\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p30t es-p10b es-p40r es-p40l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"520\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p15t\" align=\"left\">\n" +
                                                                                                        mailContent +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    public static String getMailTemplateInviteJoinWebPage() {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <style>\n" +
                "        /* CONFIG STYLES Please do not delete and edit CSS styles below */\n" +
                "/* IMPORTANT THIS STYLES MUST BE ON FINAL EMAIL */\n" +
                "#outlook a {\n" +
                "    padding: 0;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass {\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                ".ExternalClass,\n" +
                ".ExternalClass p,\n" +
                ".ExternalClass span,\n" +
                ".ExternalClass font,\n" +
                ".ExternalClass td,\n" +
                ".ExternalClass div {\n" +
                "    line-height: 100%;\n" +
                "}\n" +
                "\n" +
                ".es-button {\n" +
                "    mso-style-priority: 100 !important;\n" +
                "    text-decoration: none !important;\n" +
                "}\n" +
                "\n" +
                "a[x-apple-data-detectors] {\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-family: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "}\n" +
                "\n" +
                ".es-desk-hidden {\n" +
                "    display: none;\n" +
                "    float: left;\n" +
                "    overflow: hidden;\n" +
                "    width: 0;\n" +
                "    max-height: 0;\n" +
                "    line-height: 0;\n" +
                "    mso-hide: all;\n" +
                "}\n" +
                "\n" +
                "[data-ogsb] .es-button {\n" +
                "    border-width: 0 !important;\n" +
                "    padding: 15px 25px 15px 25px !important;\n" +
                "}\n" +
                "\n" +
                "/*\n" +
                "END OF IMPORTANT\n" +
                "*/\n" +
                "s {\n" +
                "    text-decoration: line-through;\n" +
                "}\n" +
                "\n" +
                "html,\n" +
                "body {\n" +
                "    width: 100%;\n" +
                "    -webkit-text-size-adjust: 100%;\n" +
                "    -ms-text-size-adjust: 100%;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    mso-table-lspace: 0pt;\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    border-collapse: collapse;\n" +
                "    border-spacing: 0px;\n" +
                "}\n" +
                "\n" +
                "table td,\n" +
                "html,\n" +
                "body,\n" +
                ".es-wrapper {\n" +
                "    padding: 0;\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                ".es-content,\n" +
                ".es-header,\n" +
                ".es-footer {\n" +
                "    table-layout: fixed !important;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "img {\n" +
                "    display: block;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "    text-decoration: none;\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "}\n" +
                "\n" +
                "table tr {\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "hr {\n" +
                "    Margin: 0;\n" +
                "}\n" +
                "\n" +
                "h1,\n" +
                "h2,\n" +
                "h3,\n" +
                "h4,\n" +
                "h5 {\n" +
                "    Margin: 0;\n" +
                "    line-height: 120%;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "    font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li,\n" +
                "a {\n" +
                "    -webkit-text-size-adjust: none;\n" +
                "    -ms-text-size-adjust: none;\n" +
                "    mso-line-height-rule: exactly;\n" +
                "}\n" +
                "\n" +
                ".es-left {\n" +
                "    float: left;\n" +
                "}\n" +
                "\n" +
                ".es-right {\n" +
                "    float: right;\n" +
                "}\n" +
                "\n" +
                ".es-p5 {\n" +
                "    padding: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5t {\n" +
                "    padding-top: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5b {\n" +
                "    padding-bottom: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5l {\n" +
                "    padding-left: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p5r {\n" +
                "    padding-right: 5px;\n" +
                "}\n" +
                "\n" +
                ".es-p10 {\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10t {\n" +
                "    padding-top: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10b {\n" +
                "    padding-bottom: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10l {\n" +
                "    padding-left: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p10r {\n" +
                "    padding-right: 10px;\n" +
                "}\n" +
                "\n" +
                ".es-p15 {\n" +
                "    padding: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15t {\n" +
                "    padding-top: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15b {\n" +
                "    padding-bottom: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15l {\n" +
                "    padding-left: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p15r {\n" +
                "    padding-right: 15px;\n" +
                "}\n" +
                "\n" +
                ".es-p20 {\n" +
                "    padding: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20t {\n" +
                "    padding-top: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20b {\n" +
                "    padding-bottom: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20l {\n" +
                "    padding-left: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p20r {\n" +
                "    padding-right: 20px;\n" +
                "}\n" +
                "\n" +
                ".es-p25 {\n" +
                "    padding: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25t {\n" +
                "    padding-top: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25b {\n" +
                "    padding-bottom: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25l {\n" +
                "    padding-left: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p25r {\n" +
                "    padding-right: 25px;\n" +
                "}\n" +
                "\n" +
                ".es-p30 {\n" +
                "    padding: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30t {\n" +
                "    padding-top: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30b {\n" +
                "    padding-bottom: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30l {\n" +
                "    padding-left: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p30r {\n" +
                "    padding-right: 30px;\n" +
                "}\n" +
                "\n" +
                ".es-p35 {\n" +
                "    padding: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35t {\n" +
                "    padding-top: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35b {\n" +
                "    padding-bottom: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35l {\n" +
                "    padding-left: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p35r {\n" +
                "    padding-right: 35px;\n" +
                "}\n" +
                "\n" +
                ".es-p40 {\n" +
                "    padding: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40t {\n" +
                "    padding-top: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40b {\n" +
                "    padding-bottom: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40l {\n" +
                "    padding-left: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-p40r {\n" +
                "    padding-right: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-menu td {\n" +
                "    border: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a img {\n" +
                "    display: inline-block !important;\n" +
                "}\n" +
                "\n" +
                "/* END CONFIG STYLES */\n" +
                "a {\n" +
                "    text-decoration: none;\n" +
                "}\n" +
                "\n" +
                "p,\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\n" +
                "    line-height: 150%;\n" +
                "}\n" +
                "\n" +
                "ul li,\n" +
                "ol li {\n" +
                "    Margin-bottom: 15px;\n" +
                "    margin-left: 0;\n" +
                "}\n" +
                "\n" +
                ".es-menu td a {\n" +
                "    text-decoration: none;\n" +
                "    display: block;\n" +
                "    font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\n" +
                "}\n" +
                "\n" +
                ".es-wrapper {\n" +
                "    width: 100%;\n" +
                "    height: 100%;\n" +
                "    /* background-image: ; */\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-wrapper-color {\n" +
                "    background-color: #f6f6f6;\n" +
                "}\n" +
                "\n" +
                ".es-header {\n" +
                "    background-color: #3d4c6b;\n" +
                "    /* background-image: ; */\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-header-body {\n" +
                "    background-color: #3d4c6b;\n" +
                "}\n" +
                "\n" +
                ".es-header-body p,\n" +
                ".es-header-body ul li,\n" +
                ".es-header-body ol li {\n" +
                "    color: #b7bdc9;\n" +
                "    font-size: 16px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body a {\n" +
                "    color: #b7bdc9;\n" +
                "    font-size: 16px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body {\n" +
                "    background-color: #f6f6f6;\n" +
                "}\n" +
                "\n" +
                ".es-content-body p,\n" +
                ".es-content-body ul li,\n" +
                ".es-content-body ol li {\n" +
                "    color: #999999;\n" +
                "    font-size: 16px;\n" +
                "}\n" +
                "\n" +
                ".es-content-body a {\n" +
                "    color: #75b6c9;\n" +
                "    font-size: 16px;\n" +
                "}\n" +
                "\n" +
                ".es-footer {\n" +
                "    background-color: transparent;\n" +
                "    /* background-image: ; */\n" +
                "    background-repeat: repeat;\n" +
                "    background-position: center top;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body {\n" +
                "    background-color: transparent;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body p,\n" +
                ".es-footer-body ul li,\n" +
                ".es-footer-body ol li {\n" +
                "    color: #999999;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-footer-body a {\n" +
                "    color: #999999;\n" +
                "    font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock,\n" +
                ".es-infoblock p,\n" +
                ".es-infoblock ul li,\n" +
                ".es-infoblock ol li {\n" +
                "    line-height: 120%;\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                ".es-infoblock a {\n" +
                "    font-size: 12px;\n" +
                "    color: #cccccc;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "    font-size: 40px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: bold;\n" +
                "    color: #444444;\n" +
                "}\n" +
                "\n" +
                "h2 {\n" +
                "    font-size: 28px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #444444;\n" +
                "}\n" +
                "\n" +
                "h3 {\n" +
                "    font-size: 18px;\n" +
                "    font-style: normal;\n" +
                "    font-weight: normal;\n" +
                "    color: #444444;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h1 a,\n" +
                ".es-content-body h1 a,\n" +
                ".es-footer-body h1 a {\n" +
                "    font-size: 40px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h2 a,\n" +
                ".es-content-body h2 a,\n" +
                ".es-footer-body h2 a {\n" +
                "    font-size: 28px;\n" +
                "}\n" +
                "\n" +
                ".es-header-body h3 a,\n" +
                ".es-content-body h3 a,\n" +
                ".es-footer-body h3 a {\n" +
                "    font-size: 18px;\n" +
                "}\n" +
                "\n" +
                "a.es-button,\n" +
                "button.es-button {\n" +
                "    border-style: solid;\n" +
                "    border-color: #75b6c9;\n" +
                "    border-width: 15px 25px 15px 25px;\n" +
                "    display: inline-block;\n" +
                "    background: #75b6c9;\n" +
                "    border-radius: 28px;\n" +
                "    font-size: 18px;\n" +
                "    font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\n" +
                "    font-weight: normal;\n" +
                "    font-style: normal;\n" +
                "    line-height: 120%;\n" +
                "    color: #ffffff;\n" +
                "    text-decoration: none;\n" +
                "    width: auto;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".es-button-border {\n" +
                "    border-style: solid solid solid solid;\n" +
                "    border-color: #75b6c9 #75b6c9 #75b6c9 #75b6c9;\n" +
                "    background: #75b6c9;\n" +
                "    border-width: 1px 1px 1px 1px;\n" +
                "    display: inline-block;\n" +
                "    border-radius: 28px;\n" +
                "    width: auto;\n" +
                "}\n" +
                "\n" +
                "/* RESPONSIVE STYLES Please do not delete and edit CSS styles below. If you don't need responsive layout, please delete this section. */\n" +
                "@media only screen and (max-width: 600px) {\n" +
                "\n" +
                "    p,\n" +
                "    ul li,\n" +
                "    ol li,\n" +
                "    a {\n" +
                "        line-height: 150% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1,\n" +
                "    h2,\n" +
                "    h3,\n" +
                "    h1 a,\n" +
                "    h2 a,\n" +
                "    h3 a {\n" +
                "        line-height: 120% !important;\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "        font-size: 30px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h2 {\n" +
                "        font-size: 26px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    h3 {\n" +
                "        font-size: 20px !important;\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h1 a,\n" +
                "    .es-content-body h1 a,\n" +
                "    .es-footer-body h1 a {\n" +
                "        font-size: 30px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h2 a,\n" +
                "    .es-content-body h2 a,\n" +
                "    .es-footer-body h2 a {\n" +
                "        font-size: 26px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body h3 a,\n" +
                "    .es-content-body h3 a,\n" +
                "    .es-footer-body h3 a {\n" +
                "        font-size: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-header-body p,\n" +
                "    .es-header-body ul li,\n" +
                "    .es-header-body ol li,\n" +
                "    .es-header-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content-body p,\n" +
                "    .es-content-body ul li,\n" +
                "    .es-content-body ol li,\n" +
                "    .es-content-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-footer-body p,\n" +
                "    .es-footer-body ul li,\n" +
                "    .es-footer-body ol li,\n" +
                "    .es-footer-body a {\n" +
                "        font-size: 16px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-infoblock p,\n" +
                "    .es-infoblock ul li,\n" +
                "    .es-infoblock ol li,\n" +
                "    .es-infoblock a {\n" +
                "        font-size: 12px !important;\n" +
                "    }\n" +
                "\n" +
                "    *[class=\"gmail-fix\"] {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-c,\n" +
                "    .es-m-txt-c h1,\n" +
                "    .es-m-txt-c h2,\n" +
                "    .es-m-txt-c h3 {\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r,\n" +
                "    .es-m-txt-r h1,\n" +
                "    .es-m-txt-r h2,\n" +
                "    .es-m-txt-r h3 {\n" +
                "        text-align: right !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-l,\n" +
                "    .es-m-txt-l h1,\n" +
                "    .es-m-txt-l h2,\n" +
                "    .es-m-txt-l h3 {\n" +
                "        text-align: left !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-txt-r img,\n" +
                "    .es-m-txt-c img,\n" +
                "    .es-m-txt-l img {\n" +
                "        display: inline !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-button-border {\n" +
                "        display: block !important;\n" +
                "    }\n" +
                "\n" +
                "    a.es-button,\n" +
                "    button.es-button {\n" +
                "        font-size: 20px !important;\n" +
                "        display: block !important;\n" +
                "        border-width: 15px 25px 15px 25px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-btn-fw {\n" +
                "        border-width: 10px 0px !important;\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adaptive table,\n" +
                "    .es-btn-fw,\n" +
                "    .es-btn-fw-brdr,\n" +
                "    .es-left,\n" +
                "    .es-right {\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-content table,\n" +
                "    .es-header table,\n" +
                "    .es-footer table,\n" +
                "    .es-content,\n" +
                "    .es-footer,\n" +
                "    .es-header {\n" +
                "        width: 100% !important;\n" +
                "        max-width: 600px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-adapt-td {\n" +
                "        display: block !important;\n" +
                "        width: 100% !important;\n" +
                "    }\n" +
                "\n" +
                "    .adapt-img {\n" +
                "        width: 100% !important;\n" +
                "        height: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0 {\n" +
                "        padding: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0r {\n" +
                "        padding-right: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0l {\n" +
                "        padding-left: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0t {\n" +
                "        padding-top: 0px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p0b {\n" +
                "        padding-bottom: 0 !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-m-p20b {\n" +
                "        padding-bottom: 20px !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-mobile-hidden,\n" +
                "    .es-hidden {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden,\n" +
                "    td.es-desk-hidden,\n" +
                "    table.es-desk-hidden {\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        float: none !important;\n" +
                "        max-height: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    tr.es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-desk-hidden {\n" +
                "        display: table !important;\n" +
                "    }\n" +
                "\n" +
                "    td.es-desk-menu-hidden {\n" +
                "        display: table-cell !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-menu td {\n" +
                "        width: 1% !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-table-not-adapt,\n" +
                "    .esd-block-html table {\n" +
                "        width: auto !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    table.es-social td {\n" +
                "        display: inline-block !important;\n" +
                "    }\n" +
                "\n" +
                "    .es-desk-hidden {\n" +
                "        display: table-row !important;\n" +
                "        width: auto !important;\n" +
                "        overflow: visible !important;\n" +
                "        max-height: inherit !important;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "/* END RESPONSIVE STYLES */\n" +
                "    </style>\n" +
                "    <title></title>\n" +
                "    <!--[if (mso 16)]>\n" +
                "    <style type=\"text/css\">\n" +
                "    a {text-decoration: none;}\n" +
                "    </style>\n" +
                "    <![endif]-->\n" +
                "    <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->\n" +
                "    <!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "    <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG></o:AllowPNG>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "    </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"es-wrapper-color\">\n" +
                "        <!--[if gte mso 9]>\n" +
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n" +
                "</v:background>\n" +
                "<![endif]-->\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
                "                        <table class=\"es-content esd-header-popover\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe esd-checked\" style=\"background-image:url(https://tlr.stripocdn.email/content/guids/CABINET_729b6a94015d410538fa6f6810b21b85/images/9701519718227204.jpg);background-color: #3d4c6b; background-position: left top; background-repeat: no-repeat; background-size: cover;\" bgcolor=\"#3d4c6b\" align=\"center\" background=\"https://tlr.stripocdn.email/content/guids/CABINET_729b6a94015d410538fa6f6810b21b85/images/9701519718227204.jpg\">\n" +
                "                                        <table class=\"es-content-body\" style=\"background-color: transparent;\" width=\"640\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#f6f6f6\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p10t es-p20r es-p20l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"600\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-image es-p40t es-p25b\" align=\"center\" style=\"font-size: 0px;\"><a href=\"https://viewstripo.email\" target=\"_blank\"><img src=\"https://demo.stripocdn.email/content/guids/8a8931f5-ee66-469e-bc99-eb6e62ac484f/images/logoneremovebgpreview.png\" style=\"display: block;\" alt=\"Logo\" title=\"Logo\" width=\"50\"></a></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"center\" class=\"esd-block-text es-p25t es-p30b\">\n" +
                "                                                                                        <h1 style=\"color: #ffffff;\">Invitation</h1>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p20t es-p15b es-p20r es-p20l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"600\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td esdev-links-color=\"#b7bdc9\" class=\"esd-block-text es-p15t es-p20b\" align=\"center\">\n" +
                "                                                                                        <p style=\"color: #b7bdc9; font-family: 'open sans', 'helvetica neue', helvetica, arial, sans-serif;\">Bọn tao mời mày vào trang web<br>Nút đăng ký ngay dưới<br></p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-button es-p5t es-p40b\" align=\"center\"><span class=\"es-button-border\"><a href=\"https://www.google.com.vn/\" class=\"es-button\" target=\"_blank\" style=\"color: #ffffff;\">Register now →</a></span></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer esd-footer-popover\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"6564\" align=\"center\">\n" +
                "                                        <table class=\"es-footer-body\" width=\"640\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p40t es-p40b es-p20r es-p20l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"600\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-image es-p5b\" align=\"center\" style=\"font-size: 0px;\"><a target=\"_blank\" href=\"https://viewstripo.email\"><img src=\"https://demo.stripocdn.email/content/guids/8a8931f5-ee66-469e-bc99-eb6e62ac484f/images/logoneremovebgpreview.png\" alt=\"Logo\" style=\"display: block;\" title=\"Logo\" width=\"35\"></a></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p15t es-p15b\" align=\"center\">\n" +
                "                                                                                        <p>Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }
}
