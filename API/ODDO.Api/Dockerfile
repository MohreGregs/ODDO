FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["ODDO/API/ODDOApi/ODDOApi.csproj", "ODDOApi/"]
RUN dotnet restore "API/ODDOApi/ODDOApi.csproj"
COPY . .
WORKDIR "/src/ODDOApi"
RUN dotnet build "ODDOApi.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "ODDOApi.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "ODDOApi.dll"]
