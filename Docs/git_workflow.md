# Git Workflow — CombateDeSumos

## Ramas del proyecto

| Rama | Propósito |
|------|-----------|
| `master` | Código estable y funcional listo para entrega |
| `valentina` | Desarrollo de Valentina Aguilar |
| `sergio` | Desarrollo de Sergio Vanegas |
| `sebastian` | Desarrollo de Luis Sebastián Correa |

## Flujo de trabajo

1. Cada integrante trabaja en su rama personal.
2. Se hace `commit` frecuentemente con mensajes descriptivos.
3. Antes de hacer `merge` a `master` se revisa el código con el equipo.
4. Los `merge` a `master` requieren que el proyecto compile sin errores.

## Convención de commits

```
<tipo>: <descripción breve>
```

Tipos:
- `feat`: nueva funcionalidad
- `fix`: corrección de error
- `docs`: cambios en documentación
- `refactor`: refactorización sin cambio de comportamiento
- `style`: formato, sin cambio de lógica

**Ejemplos:**
```
feat: agregar envío de datos del luchador al servidor
fix: corregir lectura de float en HiloLuchador
docs: agregar JavaDoc a ControlDojo
```

## Comandos básicos

```bash
# Clonar el repositorio
git clone <url>

# Crear y cambiar a una rama
git checkout -b nombre-rama

# Ver estado de los cambios
git status

# Agregar cambios
git add .

# Hacer commit
git commit -m "feat: descripción del cambio"

# Subir rama al repositorio remoto
git push origin nombre-rama

# Actualizar la rama local desde master
git pull origin master
```
