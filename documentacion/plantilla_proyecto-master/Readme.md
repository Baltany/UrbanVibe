# Proyecto de Desarrollo de Software

Podemos generar un libro en formato imprenta con este comando:

```bash
docker run --rm \
       --volume "$(pwd):/data" \
       --user $(id -u):$(id -g) \
       pandoc/extra 0-*.md  -o \
       Libro.pdf --template eisvogel --listings --number-sections
```

Para generar la presentación en HTML (desde la carpeta diapositivas):

```bash
docker run --rm -v $PWD:/home/marp/app/ -e LANG=$LANG marpteam/marp-cli presentacion.md
```

## Extensiones de VS Code

* Marp for VS Code
* Git Graph
* Markdown All in One
* Markdown Preview Enhanced
* Markdown Shortcuts
* Java Pack
* Spring-boot
* Docker
* Database(Connector)
* Beauty(Dar formato al código)
* Error Lens(Indica los errores en el código)
* Gitlab Workflow
* IntellijCode
* Tabnine(ID)
* Markdownlint
* Dracula(UI de Vscode)
* Autoclose tag(HTML)
