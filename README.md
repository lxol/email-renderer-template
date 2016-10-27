# email-renderer-template

[![Build Status](https://travis-ci.org/hmrc/email-renderer-template.svg)](https://travis-ci.org/hmrc/email-renderer-template) [ ![Download](https://api.bintray.com/packages/hmrc/releases/email-renderer-template/images/download.svg) ](https://bintray.com/hmrc/releases/email-renderer-template/_latestVersion)

Sample email renderer service for rendering emails for  given template ID. 
This will be utilised by email service to render and then send emails.

# API
| **Path**                     | **Supported Methods** | **Description** |
| ---------------------------- | --------------------- | --------------- |
| ```/templates/:templateId``` | POST                  | Render the email using the template for templateId [More...](#post-templatestemplateId) |

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
    "plain": "Rendered template in text format",
    "html": "Rendered template in HTML format",
    "fromAddress": "From address to be used for this email when sending",
    "subject": "Email subject to use", 
    "service": "The regime (i.e. - sa/paye/etc) that this template belongs to"
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
 
### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
