.PHONY: build format run

build:
	./gradlew build

format:
	./gradlew ktlintFormat

run:
	./gradlew bootRun
