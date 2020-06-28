package br.com.fiap.javaplatform.main;

import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterConnectionTest {

	public static void main(String[] args) {

		String mensagem = "Teste de tweet via API";
		System.out.println("Iniciando teste de conexão com o Twitter.");
		System.out.println("Conta padrão: @ceduardoroque");

		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		// System.out.println("Postando nova mensagem de status: " + mensagem);

		// Fazer um tweet
		// status = twitter.updateStatus(mensagem);

		// recuperar a timeline da conta
		// getTimeline();

		// Fazer uma busca
		/*
		 * try { searchTest(); } catch (TwitterException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		// Maniplando o stream do twitter
		StatusListener listener = new StatusListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onStatus(Status status) {
				// try {
				// do something and update own status
				String screenName;
				try {
					screenName = twitterStream.getScreenName();

					StatusUpdate update = new StatusUpdate("É mesmo!");
					update.setInReplyToStatusId(status.getId());

					twitter.updateStatus(update);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// } catch (TwitterException e) {
				// e.printStackTrace();
				// }
				System.out.println(status.getText());
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// System.out.println("Got a status deletion notice id:" +
				// tatusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:"
				// + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};

		// try {

		// filtering for ourselves here
		// long[] userFilter = { twitter.getId() };

		String[] termFilter = { "ceduardoroque" };
		FilterQuery filter = new FilterQuery(termFilter);
		twitterStream.addListener(listener);
		twitterStream.filter(filter);

		// FilterQuery query = new FilterQuery();
		// query.follow(new );
		// twitterStream.filter(query);
		/*
		 * } catch (IllegalStateException | TwitterException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

	}

	private static void getTimeline() throws TwitterException {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = twitter.getHomeTimeline();
		System.out.println("Showing home timeline.");
		for (Status status : statuses) {
			System.out.println(status.getUser().getName() + ":" + status.getText());
		}
	}

	private static void searchTest() throws TwitterException {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query("ceduardoroque");
		QueryResult result = twitter.search(query);
		List<Status> tweets = result.getTweets();

		if (!tweets.isEmpty())
			for (Status status : tweets) {
				System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			}
		else
			System.out.println("Não foram encontrados tweets para esta pesquisa: " + query.getQuery());
	}
}
