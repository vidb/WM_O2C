package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-01-03 13:45:55 PST
// -----( ON-HOST: eng-114.activesw.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Date;
import java.io.*;
import com.wm.app.b2b.server.*;
import iaik.asn1.*;
import java.security.cert.*;
import java.security.*;
// --- <<IS-END-IMPORTS>> ---

public final class ssl

{
	// ---( internal utility methods )---

	final static ssl _instance = new ssl();

	static ssl _newInstance() { return new ssl(); }

	static ssl _cast(Object o) { return (ssl)o; }

	// ---( server methods )---




	public static final void extractCertificate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(extractCertificate)>> ---
		// @sigtype java 3.5
		// [i] object:0:required certificate
		// [o] field:0:required subjectDN
		// [o] field:0:required issuerDN
		// [o] field:0:required issuerSN
		// [o] field:0:required notBefore
		// [o] field:0:required notAfter
	IDataCursor idcPipeline = pipeline.getCursor();
	byte[] certificate = null;
	if (idcPipeline.first("certificate"))
	{
		certificate = (byte[])idcPipeline.getValue();
	}
	try
	{
		ByteArrayInputStream inStream = new ByteArrayInputStream(certificate);
	
 		CertificateFactory cf = CertificateFactory.getInstance("X.509");
 		X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
 		inStream.close();

		String subjectDN = ((Principal)(cert.getSubjectDN())).getName();
		String issuerDN = ((Principal)(cert.getIssuerDN())).getName();
		String issuerSN = String.valueOf(cert.getSerialNumber().intValue());
		Date notBefore = cert.getNotBefore();
		Date notAfter = cert.getNotAfter();

		idcPipeline.insertAfter("subjectDN", subjectDN);
		idcPipeline.insertAfter("issuerDN", issuerDN);
		idcPipeline.insertAfter("issuerSN", issuerSN);
		idcPipeline.insertAfter("notBefore", notBefore.toString());
		idcPipeline.insertAfter("notAfter", notAfter.toString());
	}
	catch(Exception e)
	{
		throw new ServiceException (e.getMessage());
	}
	finally
	{
		idcPipeline.destroy();
	}
		// --- <<IS-END>> ---

                
	}



	public static final void extractChain (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(extractChain)>> ---
		// @sigtype java 3.5
		// [o] field:0:required hasChain
		// [o] record:1:required certificates
		// [o] - object:0:required certificate
		// [o] - field:0:required subjectDN
		// [o] - field:0:required issuerDN
		// [o] - field:0:required issuerSN
		// [o] - field:0:required notBefore
		// [o] - field:0:required notAfter

	IDataCursor idcPipeline = pipeline.getCursor();
	try
	{

		InvokeState is = InvokeState.getCurrentState();
		ProtocolInfoIf protocolInfo = is.getProtocolInfoIf();
		if (protocolInfo instanceof HTTPStateSSL)
		{
		    X509Certificate[] chain = ((HTTPStateSSL)protocolInfo).getCertificateChain();
			if (chain==null)
			{
				idcPipeline.insertAfter("hasChain", "false");
			}
			else
			{
				idcPipeline.insertAfter("hasChain", "true");
				byte[] certInst;
				String certDecoded="";
				IData[] certs = new IData[chain.length];
		
		 		for (int i = 0; i < chain.length; i++)
				{
					IData single_result = IDataFactory.create();
					IDataCursor s_result_c = single_result.getCursor();
					X509Certificate cert = chain[i];
					certInst = cert.getEncoded();
					String subjectDN = ((Principal)(cert.getSubjectDN())).getName();
					String issuerDN = ((Principal)(cert.getIssuerDN())).getName();
					String issuerSN = String.valueOf(cert.getSerialNumber().intValue()); 
					Date notBefore = cert.getNotBefore();
					Date notAfter = cert.getNotAfter();

					s_result_c.insertAfter("subjectDN", subjectDN);
					s_result_c.insertAfter("issuerDN", issuerDN);
					s_result_c.insertAfter("issuerSN", issuerSN);
					s_result_c.insertAfter("notBefore", notBefore.toString());
					s_result_c.insertAfter("notAfter", notAfter.toString());
					s_result_c.insertAfter("certificate", certInst);
			
					s_result_c.destroy();
					certs[i]=single_result;
				}
				idcPipeline.insertAfter("certificates", certs);
			}

		} 

	}
	catch (Exception e)
	{
		throw new ServiceException ("Error extracting certificate chain! " + e.getMessage());
	}
	finally
	{
		idcPipeline.destroy();
	}
		// --- <<IS-END>> ---

                
	}



	public static final void importUserCertificate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(importUserCertificate)>> ---
		// @sigtype java 3.5
		// [i] field:0:required certificatePath
		// [i] object:0:required certificate
		// [i] field:0:required user
		// [o] field:0:required certificateIssuerCN
		// [o] field:0:required certificateSubjectCN
		// [o] field:0:required certificateSerialNumber
		// [o] field:0:required errorMsg

	String	certificatePath = null;
	String	user = null;

	String certificateIssuer = null;;
	String certificateSerialNumber = null;
	String certificateSubject = null;
	byte[] certificate = null;
	iaik.x509.X509Certificate x509certificate = null;

	// pipeline
	IDataCursor pipelineCursor = pipeline.getCursor();
	if ( pipelineCursor.first( "certificatePath" ) )
	{
		certificatePath = (String) pipelineCursor.getValue();
	}
	if ( pipelineCursor.first( "certificate" ) )
	{
		certificate = (byte[]) pipelineCursor.getValue();
	}

	if ( pipelineCursor.first( "user" ) )
	{
		user = (String) pipelineCursor.getValue();
	}

	// pipeline
	if (  user == null )
	{
	    pipelineCursor.insertAfter("errorMsg", "null input user.");	
		return;
	}

	if ( certificatePath ==  null && certificate ==  null)
	{
	    pipelineCursor.insertAfter("errorMsg","Either certificatePath and certificate can not be null.");	
		return;
	}

	if (  user.equals("Administrator") )
	{
	    pipelineCursor.insertAfter("errorMsg","Import denied: Cannot map to Administrator.");	
		return;
	}


	try
	{
		if ( certificatePath !=  null)
		{
	   		java.io.File file = new java.io.File(certificatePath);
	    	if(!file.exists())
	    	{
	        	pipelineCursor.insertAfter("errorMsg","certificate doesn't exist.");
				return;
	    	}
    
	    	if(file.length() == 0L)
	    	{
	       		pipelineCursor.insertAfter("errorMsg","certificate is empty.");
				return;
	    	}

	    	//create certificate
	    	java.io.FileInputStream fileinputstream = new java.io.FileInputStream(file);
	    	x509certificate = new iaik.x509.X509Certificate(fileinputstream);
	    	fileinputstream.close();    
		}
		else if (certificate !=  null)
		{
			x509certificate = new iaik.x509.X509Certificate(certificate);
		}

    	// check if certificate exists 
	    String issuer = x509certificate.getIssuerDN().toString();
		String serialNumber = x509certificate.getSerialNumber().toString();
		certificateIssuer = issuer;;
		certificateSerialNumber = serialNumber;
	    certificateSubject = x509certificate.getSubjectDN().toString();

  
	    com.wm.app.b2b.server.CertificateMapEntry acertificatemapentry[] = 
	    com.wm.app.b2b.server.CertificateMapper.current().listMappings();
	    if(acertificatemapentry != null && acertificatemapentry.length != 0)
	    {
	        for(int i = 0; i < acertificatemapentry.length; i++)
	        {
	            java.security.cert.X509Certificate aX509certificate = 
	                acertificatemapentry[i].getCertificate();
	            if( serialNumber.equals(aX509certificate.getSerialNumber().toString()) && 
	                issuer.equals(aX509certificate.getIssuerDN().toString()))
	            {
	                pipelineCursor.insertAfter("errorMsg","certificate already exists.");
					return;
	            }
	        }
	    }

	    // add certificate to the mapping list
	    com.wm.app.b2b.server.User serverUser =  com.wm.app.b2b.server.UserManager.getUser(user);
	    if(! com.wm.app.b2b.server.CertificateMapper.current().addMapping(serverUser, x509certificate))
	    {
	        pipelineCursor.insertAfter("errorMsg","error in importing certificate as " + user);
			return;
	    } 
	}
	catch (Exception e)
	{
	    pipelineCursor.insertAfter("errorMsg",e.toString());
		return;
	}
 

	int startIndex = certificateIssuer.indexOf("CN=");
	int endIndex = certificateIssuer.indexOf(",", startIndex);
	certificateIssuer = certificateIssuer.substring(startIndex+3, endIndex);


	startIndex = certificateSubject.indexOf("CN=");
	endIndex = certificateSubject.indexOf(",", startIndex);
	certificateSubject = certificateSubject.substring(startIndex+3, endIndex);

	// pipeline
	pipelineCursor = pipeline.getCursor();
	pipelineCursor.last();
	pipelineCursor.insertAfter( "certificateIssuerCN", certificateIssuer );
	pipelineCursor.last();
	pipelineCursor.insertAfter( "certificateSubjectCN", certificateSubject );
	pipelineCursor.last();
	pipelineCursor.insertAfter( "certificateSerialNumber", certificateSerialNumber );
	pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}
}

