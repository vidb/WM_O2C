package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2004-02-29 22:14:55 PST
// -----( ON-HOST: 192.168.0.3

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.client.TContext;
import java.util.Enumeration;
import com.wm.app.b2b.client.Context;
import com.wm.app.b2b.server.ServerAPI;
// --- <<IS-END-IMPORTS>> ---

public final class remote

{
	// ---( internal utility methods )---

	final static remote _instance = new remote();

	static remote _newInstance() { return new remote(); }

	static remote _cast(Object o) { return (remote)o; }

	// ---( server methods )---



    public static final Values getRemoteServerInfoHelper (Values in)
    {
        Values out = in;
		// --- <<IS-START(getRemoteServerInfoHelper)>> ---
		// @sigtype java 3.0
		// [i] field:0:required alias
		// [i] record:0:required servers
		// [o] field:0:required host
		// [o] field:0:required port
		// [o] field:0:required user
		// [o] field:0:required pass
		// [o] field:0:required ssl
		// [o] field:0:required privKeyFile
		// [o] field:0:required certs

	Values servers = in.getValues("servers");
	String alias = in.getString("alias");

	Values serverInfo = servers.getValues(alias);

	if (serverInfo == null)
	{
		out = Service.throwError("Remote server alias \"" + alias + "\" does not exist.");
		return out;
	}

	String host = serverInfo.getString("host");
	String port = serverInfo.getString("port");
	String user = serverInfo.getString("user");
	String pass = serverInfo.getString("pass");
	String ssl = serverInfo.getString("ssl");
	String privKeyFile = serverInfo.getString("privKeyFile");
	String certFiles = serverInfo.getString("certFiles");

	out.put("host", host);
	out.put("port", port);
	out.put("user", user);
	out.put("pass", pass);
	out.put("ssl", ssl);
	out.put("privKeyFile", privKeyFile);
	out.put("certs", certFiles);
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void runContextJob (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(runContextJob)>> ---
		// @sigtype java 3.5
		// [i] field:0:required host
		// [i] field:0:required port
		// [i] field:0:required folder
		// [i] field:0:required service
		// [i] field:0:required username
		// [i] field:0:required password
		// [i] field:0:required useSSL {"false","true"}
		// [i] field:0:required privKeyFile
		// [i] field:1:required certFiles
	IDataCursor idcPipeline = pipeline.getCursor();

	String host = "";
	String port = "";
	String folderName = "";
	String serviceName = "";
	if (idcPipeline.first( "host" ))
	{
		host = (String) idcPipeline.getValue();
	}
	if (idcPipeline.first( "port" ))
	{
		port = (String) idcPipeline.getValue();
	}
	if (idcPipeline.first( "folder" ))
	{
		folderName = (String) idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Folder cannot be null!");
	}
	if (idcPipeline.first( "service" ))
	{
		serviceName = (String) idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Service cannot be null!");
	}

	idcPipeline.first( "username" );
	String username = (String) idcPipeline.getValue();
	idcPipeline.first( "password" );
	String password = (String) idcPipeline.getValue();

	idcPipeline.first( "useSSL" );
	String useSSL = (String) idcPipeline.getValue();
	idcPipeline.first( "privKeyFile" );
	String privKeyFile = (String) idcPipeline.getValue();
	String certFiles[] = null; 
	if (idcPipeline.first( "certFiles" ))
		certFiles = (String []) idcPipeline.getValue();

	try
	{
		String URL = "";
		ServerAPI api = new ServerAPI();
		if (host == null)
			host = api.getServerName();

		if (port == null)
			port = Integer.toString(api.getCurrentPort());

		if (port.equals("-1"))
			URL = host;
		else
			URL = host + ":" + port;

		Context context = new Context();
		if (useSSL.equals("true"))
		{
			context.setSecure(true);
		}
		if ((privKeyFile != null) && (certFiles != null))
		{
			context.setSSLCertificates(privKeyFile, certFiles);
		}

		context.connect(URL, username, password);
		IData results = context.invoke(folderName, serviceName, pipeline);
		IDataUtil.merge(results, pipeline);

		context.disconnect();

	}
	catch (Exception e)
	{
		throw new ServiceException(e);
	}

	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void runTContextJob (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(runTContextJob)>> ---
		// @sigtype java 3.5
		// [i] field:0:required host
		// [i] field:0:required port
		// [i] field:0:required folder
		// [i] field:0:required service
		// [i] field:0:required username
		// [i] field:0:required password
		// [i] field:0:required ttl
		// [i] field:0:required retries
		// [i] field:0:required useSSL {"false","true"}
		// [i] field:0:required privKeyFile
		// [i] field:1:required certFiles
		// [i] field:0:optional async? {"true","false"}
	IDataCursor idcPipeline = pipeline.getCursor();

	int ttl = 0; // if 0 is used, default ttl is used
	int retries = 0;

	String host = "";
	String port = "";
	String folderName = "";
	String serviceName = "";
	if (idcPipeline.first( "host" ))
	{
		host = (String) idcPipeline.getValue();
	}
	if (idcPipeline.first( "port" ))
	{
		port = (String) idcPipeline.getValue();
	}
	if (idcPipeline.first( "folder" ))
	{
		folderName = (String) idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Folder cannot be null!");
	}
	if (idcPipeline.first( "service" ))
	{
		serviceName = (String) idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Service cannot be null!");
	}

	idcPipeline.first( "username" );
	String username = (String) idcPipeline.getValue();
	idcPipeline.first( "password" );
	String password = (String) idcPipeline.getValue();

	idcPipeline.first( "useSSL" );
	String useSSL = (String) idcPipeline.getValue();
	idcPipeline.first( "privKeyFile" );
	String privKeyFile = (String) idcPipeline.getValue();
	String certFiles[] = null;
	if (idcPipeline.first( "certFiles" ))
		certFiles = (String []) idcPipeline.getValue();
	idcPipeline.first( "async?" );
	String async = (String) idcPipeline.getValue();

	if (idcPipeline.first( "ttl" ))
	{
		String TTL = (String)idcPipeline.getValue();
		ttl = Integer.parseInt(TTL);
	}
	if (idcPipeline.first( "retries" ) )
	{
		String Retries = (String) idcPipeline.getValue();
		retries = Integer.parseInt(Retries);
	}

	idcPipeline.destroy();

	//---Initialization----------------------------------------------------
	try
	{
		String URL = "";
		ServerAPI api = new ServerAPI();
		if (host == null)
			host = api.getServerName();
		if (port == null)
			port = Integer.toString(api.getCurrentPort());

		if (port.equals("-1"))
			URL = host;
		else
			URL = host + ":" + port;
	
		//TContext initialization 
		TContext tx = new TContext();
		if (useSSL.equals("true"))
		{
			tx.setSecure(true);
		}

		if ((privKeyFile != null) && (certFiles != null))
		{
			tx.setSSLCertificates(privKeyFile, certFiles);
		}

		tx.connect(URL, username, password);
		
		//start transaction
		String tid = tx.startTx(ttl, retries);
		if (async.equalsIgnoreCase("true"))
		{
	        tx.submitTx(tid, folderName, serviceName, pipeline);
		}
		else
		{
		        IData results = tx.invokeTx(tid, folderName, serviceName, pipeline);
			IDataUtil.merge(results, pipeline);

		}

        tx.endTx(tid);

		tx.disconnect();
 
	}
	catch (Exception e)
	{
		throw new ServiceException(e);
	}

		// --- <<IS-END>> ---

                
	}
}

