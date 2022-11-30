FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["ODDO.Api/ODDO.Api.csproj", "ODDO.Api/"]
RUN dotnet restore "ODDO.Api/ODDO.Api.csproj"
COPY . .
WORKDIR "/src/ODDO.Api"
RUN dotnet build "ODDO.Api.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "ODDO.Api.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "ODDO.Api.dll"]
