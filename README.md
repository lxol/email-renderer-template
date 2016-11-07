# email-renderer-template

[![Build Status](https://travis-ci.org/hmrc/email-renderer-template.svg)](https://travis-ci.org/hmrc/email-renderer-template) [ ![Download](https://api.bintray.com/packages/hmrc/releases/email-renderer-template/images/download.svg) ](https://bintray.com/hmrc/releases/email-renderer-template/_latestVersion)

To send emails via the HMRC email service, clients must create an email rendering service similar to this template.
This will be utilised by email service to render and then send emails.

# API
| **Path**                     | **Supported Methods** | **Description** |
| ---------------------------- | --------------------- | --------------- |
| ```/templates/:templateId``` | POST                  | Render the email using the template for templateId [More...](#post-templatestemplateId) |
| ```/email-renderer-template/test-only/preview``` | GET                   | Home page to preview all the templates supported by this renderer [More...](#get-email-renderer-templatetest-onlypreview) |

# POST /templates/:templateId
 
 Renders the email content using the template for the given templateId, using the parameters values provided in the request body.
 
 ``` json
 {
    "parameters": {
      "param1" : "Parameter to be used in the email template"
      "param2" : "Parameter to be used in the email template"
    }
  }
  ```

Responds with status:

* 200 When the template is rendered successfully

 ```json
{
    "plain": "Base64 encoded rendered template in text format",
    "html": "Base64 encode endered template in HTML format",
    "fromAddress": "From address to be used for this email when sending",
    "subject": "Email subject to use", 
    "service": "The type of service that this template belongs to (for hmrc, this corresponds to regime, i.e. sa/paye/etc)",
    "priority": "the priority queue that the email service should route the message to { urgent|standard|background }"
}
 ```
* 404 When the template with the provided ID cannot be resolved.
 
* 400 When an insufficient set of parameters for rendering the template is specified in the request. Only the first missing value is reported.

 ```json
{
    "status": "Rendering of template failed",
    "reason": "key not found: param10"
}
 ```

# GET /email-renderer-template/test-only/preview

During development of templates, the final display of each template can be verified using the preview end point. 
This shows how the template will be rendered on an email client using sample parameters.

For representation on multiple email clients and browsers, please use [Litmus](https://litmus.com/email-testing)

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
