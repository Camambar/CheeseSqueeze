#How to set up the project in eclipse:
1.	Download and install a copy of the android sdk (https://developer.android.com/sdk/index.html?hl=i#download)
2.	Download and run libgdx (http://libgdx.badlogicgames.com/)
	*	Fill the screen in like this and hit advanced:
		![libgdx setup screen](http://i.imgur.com/gIqCe6V.jpg)
	*	Select eclipse under advanced settings, and hit save:
		![libgdx advanced screen](http://i.imgur.com/Nmm8X0l.jpg)
3.	Make sure you have a working copy of git and issue these commands:
	*	cd /path/to/your/CheeseSqueeze/folder
	*	git init
	*	git remote add origin https://github.com/Camambar/CheeseSqueeze.git
	*	git fetch
	*	git checkout -t -f origin/master
	*	git pull

	In case of problems:
	*       git remote rm origin
	*	rm -rf .git
4.	Fire up your copy of eclipse (https://www.eclipse.org/downloads/)
	*	Make sure it has the egit plugin (standard included in the latest eclipse versions)
	*	open eclipse:
		![eclipse workspace selector](http://i.imgur.com/Tg6eIJo.jpg)
	*	click file>import and select 'Projects from Git' and hit Next:
		![import Projects from Git](http://i.imgur.com/Zcxg9aO.jpg)
	*	click 'Existing local repository' and hit Next:
		![existing local repository](http://i.imgur.com/2EQe0Ed.jpg)
	*	Hit add on the next screen, and browse for your CheeseSqueeze folder. Hit finnish.
		![browse for local cheesesqueeze](http://i.imgur.com/r9Szusz.jpg)
	*	Click Next, hit the Import existing projects radio button, and hit next.
		![import existing projects](http://i.imgur.com/PMNxMQc.jpg)
	*	select all 5 projects, and hit finnish.
5.	Fix up your build paths
	*	right click the android project, and click configure build path:
		![configure android build path](http://i.imgur.com/WLoRP2N.jpg)
	*	Hit the add external JARs button under the libraries tab, and search for your android sdk:
		![search for android](http://i.imgur.com/yx7bmSz.jpg)
	*	right click the core project, and click configure build path:
	*	Hit the add JARs button, and add both tween engine jars (they are located inside the android project folder)
		![add the tween engine jars](http://i.imgur.com/05wYhf1.jpg)
