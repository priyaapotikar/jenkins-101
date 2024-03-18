pipeline{
	agent any
	stages{
		stage("git clone"){
			steps{
				dir('project/')

				cleanWs()
				sh "git clone https://github.com/deepkiraninc/tlchat/"
			}
		}
	
		stage(Build){
			steps{
				dir('project/tlchat') {
				sh "mvn clean install"
			}
		}
		}
	
		stage (Deploy) {
			steps{
				sh "sudo pm2 flush"
				sh "cd   /home/ubuntu/tl-chat-jenkins"
				sh "unzip  TL-Chat\ Production.zip  -d   ."
				sh "cp  -R  ./TL-Chat\ Production/*   ."
				sh "rm  -rf   ./TL-Chat\ Production/"
				sh "sudo npm i"
				sh "sudo pm2 restart all"
				sh "sudo pm2 status"
			}
		}
	}
}