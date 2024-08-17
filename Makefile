runn:
	@echo "Compiling files ⏳"
	@echo "--------------------------------------------------"
	cd sip/bisoke/one && javac -d . -cp . models/*.java utils/*.java BisokeLPT.java && java sip.bisoke.one.BisokeLPT